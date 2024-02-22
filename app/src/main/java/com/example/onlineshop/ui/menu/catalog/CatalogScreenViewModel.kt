package com.example.onlineshop.ui.menu.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.ProductsInfoRepository
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.model.CommodityImages
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

data class CatalogScreenUiState(
    val isExpanded : Boolean = false,
    val sortType : String = "По популярности",
    val listOfTypes : List<String> = listOf("По популярности" , "По уменьшению цены" , "По возрастанию цены"),
    val listOfTags : List<String> = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски"),
    val currentTag : String = "Смотреть все",
    val listOfProductsOriginal: List<CommodityItem> = listOf(),
    val listOfProducts: List<CommodityItem> = listOf()
)

sealed interface CatalogScreenCommodityItemsUiState {
    object Success : CatalogScreenCommodityItemsUiState
    object Error : CatalogScreenCommodityItemsUiState
    object Loading : CatalogScreenCommodityItemsUiState
}


class CatalogScreenVIewModel(
    private val usersRepository: UsersRepository,
    private val productsInfoRepository: ProductsInfoRepository
) : ViewModel() {

    var catalogScreenCommodityItemsUiState: CatalogScreenCommodityItemsUiState by mutableStateOf(CatalogScreenCommodityItemsUiState.Loading)
        private set

    private val _catalogScreenUiState = MutableStateFlow(CatalogScreenUiState())
    var catalogScreenUiState: StateFlow<CatalogScreenUiState> = _catalogScreenUiState
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
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CatalogScreenUiState()
        )

    init {
        getCommodityItemsInfo()
    }
    /**
     * Gets Items information from the API Retrofit
     */
    private fun getCommodityItemsInfo() {
        viewModelScope.launch {
            catalogScreenCommodityItemsUiState = try {
                //получаем List с описанием товарных элементов
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
                CatalogScreenCommodityItemsUiState.Success
            } catch (e: IOException) {
                CatalogScreenCommodityItemsUiState.Error
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