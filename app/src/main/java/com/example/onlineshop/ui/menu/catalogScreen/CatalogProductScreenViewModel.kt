package com.example.onlineshop.ui.menu.catalogScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.ProductsInfoRepository
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.model.CommodityImages
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.model.allCommodityPhotos
import com.example.onlineshop.network.CommodityDescription
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface CatalogScreenNetworkUiState {
    object Success : CatalogScreenNetworkUiState
    object Error : CatalogScreenNetworkUiState
    object Loading : CatalogScreenNetworkUiState
}
data class CatalogScreenUiState(
    val isExpanded : Boolean = false,
    val sortType : String = "По популярности",
    val listOfTypes : List<String> = listOf("По популярности" , "По уменьшению цены" , "По возрастанию цены"),
    val listOfTags : List<String> = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски"),
    val currentTag : String = "Смотреть все",
    val listOfProductsOriginal: List<CommodityItem> = listOf(),
    val listOfProducts: List<CommodityItem> = listOf(),
)
data class ProductScreenUiState(
    val commodityItem: CommodityItem = CommodityItem(),
    val isShowInfo: Boolean = true,
    val isShowCompound: Boolean = false,
)


class CatalogProductScreenViewModel(
    private val usersRepository: UsersRepository,
    private val productsInfoRepository: ProductsInfoRepository
) : ViewModel() {

    /**
     * State для состояния Сети
     */
    var catalogScreenNetworkUiState: CatalogScreenNetworkUiState by mutableStateOf(
        CatalogScreenNetworkUiState.Loading
    )
        private set

    /**
     * State для экарана с товарами
     */
    private val _catalogScreenUiState = MutableStateFlow(CatalogScreenUiState())
    val catalogScreenUiState: StateFlow<CatalogScreenUiState> = _catalogScreenUiState
        .combine(usersRepository.getFavorites()) { localState, favorite ->
            localState.copy(
                listOfProductsOriginal = localState.listOfProductsOriginal.map { item ->
                    if (item.productDescription.id in favorite) {
                        // Создаем копию элемента с обновленным полем isFavourite
                        item.copy(isFavourite = true)
                    } else {
                        item.copy(isFavourite = false)
                    }
                },
                listOfProducts = localState.listOfProducts.map { item ->
                    if (item.productDescription.id in favorite) {
                        // Создаем копию элемента с обновленным полем isFavourite
                        item.copy(isFavourite = true)
                    } else {
                        item.copy(isFavourite = false)
                    }
                }
            )
        } //для преобразования Flow в StateFlow.
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000), //Преобразование будет запущено, когда есть хотя бы один подписчик на StateFlow, и будет продолжаться до тех пор, пока есть подписчики. Время жизни этой операции составляет 5 секунд (5000 миллисекунд
            initialValue = CatalogScreenUiState()
        )

    /**
     * State для выбранного товара
     */
    private val _productScreenUiState = MutableStateFlow(ProductScreenUiState())
    val productScreenUiState: StateFlow<ProductScreenUiState> = _productScreenUiState
        .combine(usersRepository.getFavorites()) { localState, favorite ->
            val isFavourite = localState.commodityItem.productDescription.id in favorite
            localState.copy(
                commodityItem = localState.commodityItem.copy(isFavourite = isFavourite)
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductScreenUiState()
        )


    init {
        getCommodityItemsInfo()
    }
    /**
     * Gets Items information from the API Retrofit
     */
    private fun getCommodityItemsInfo() {
        viewModelScope.launch {
            catalogScreenNetworkUiState = try {
                //получаем List с описанием товарных элементов из сети
                val listOfDescriptions : List<CommodityDescription> = productsInfoRepository.getProductsInfo().items
                //Получаем List картинок
                val listOfImages : List<CommodityImages> = allCommodityPhotos
                //Создаем List Продуктов в который будем содержать и картинки и описание и любимый товар
                val listOfProducts = mutableListOf<CommodityItem>()
                //Совединяем все в один List
                for (i in listOfDescriptions.indices) {
                    val item = listOfDescriptions[i]
                    val images = if (i < listOfImages.size) {
                        listOfImages[i]
                    } else {
                        CommodityImages()
                    }
                    listOfProducts.add(CommodityItem(productDescription = item, images = images))
                }
                _catalogScreenUiState.update {
                    it.copy(
                        listOfProductsOriginal = listOfProducts,
                        listOfProducts = listOfProducts
                    )
                }
                //фильтруем по дефолтным значениям
                val currentState = catalogScreenUiState.value
                sortTagItems(currentState.currentTag)
                sortItems(currentState.sortType)
                //состояние загрузки
                CatalogScreenNetworkUiState.Success
            } catch (e: IOException) {
                CatalogScreenNetworkUiState.Error
            }
        }
    }
    fun saveOrDeleteFromFavorites(commodityItem: CommodityItem){
        viewModelScope.launch {
            if (commodityItem.isFavourite){
                //Удаляем из БД
                usersRepository.deleteFromFavorites(commodityItem.productDescription.id)
            }else{
                // Записываем в БД
                usersRepository.insertInFavorite(commodityItem.productDescription.id)
            }
        }
    }
    fun chooseCommodityItem(item:CommodityItem) {
        _productScreenUiState.update {
            it.copy(
                commodityItem = item
            )
        }
    }
    fun hideShowInfo(){
        _productScreenUiState.update {
            it.copy(
                isShowInfo = !it.isShowInfo
            )
        }
    }
    fun hideShowCompoundInfo(){
        _productScreenUiState.update {
            it.copy(
                isShowCompound = !it.isShowCompound
            )
        }
    }
    fun expandChange(){
        _catalogScreenUiState.update {
            it.copy(
                isExpanded = !it.isExpanded
            )
        }
    }
    fun onDropdownMenuItemClick(sortType: String){
        _catalogScreenUiState.update {
            it.copy(
                sortType = sortType,
                isExpanded = !it.isExpanded
            )
        }
        sortItems(sortType)
    }
    fun onTagClick(tag : String){
        _catalogScreenUiState.update {
            it.copy(
                currentTag = tag
            )
        }
        sortTagItems(tag)
    }
    fun onEraseTagClick(){
        _catalogScreenUiState.update {
            it.copy(
                currentTag = ""
            )
        }
        //берем состояние после его обновления
        sortTagItems(catalogScreenUiState.value.currentTag)
    }

    private fun sortTagItems(tag : String){
        val currentState = catalogScreenUiState.value
        when (tag) {
            "Лицо" ->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.productDescription.tags.contains("face") }
                    )
                }
                sortItems(currentState.sortType)
            }
            "Тело" ->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.productDescription.tags.contains("body") }
                    )
                }
                sortItems(currentState.sortType)
            }
            "Загар"->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.productDescription.tags.contains("suntan") }
                    )
                }
                sortItems(currentState.sortType)
            }
            "Маски"->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.productDescription.tags.contains("mask") }
                    )
                }
                sortItems(currentState.sortType)
            }
            else ->{
                _catalogScreenUiState.update {
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal
                    )
                }
                sortItems(currentState.sortType)
            }
        }
    }
    private fun sortItems(sortType: String) {
        when (sortType) {
            "По популярности" -> {
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProducts.sortedByDescending {
                            it.productDescription.feedback?.rating ?: 0.0
                        }
                    )
                }
            }
            "По уменьшению цены" -> {
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProducts.sortedByDescending {
                            it.productDescription.price.priceWithDiscount.toInt()
                        }
                    )
                }
            }
            "По возрастанию цены" -> {
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts  = it.listOfProducts.sortedBy {
                            it.productDescription.price.priceWithDiscount.toInt()
                        }
                    )
                }
            }
        }
    }
}