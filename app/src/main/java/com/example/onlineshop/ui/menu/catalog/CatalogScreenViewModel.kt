package com.example.onlineshop.ui.menu.catalog

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.ProductsInfoRepository
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.network.CommodityItem
import com.example.onlineshop.model.GoodsImages
import com.example.onlineshop.model.GoodsItems
import com.example.onlineshop.model.allGoodsPhotos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class CatalogScreenUiState(
    val isExpanded : Boolean = false,
    val sortType : String = "По популярности",
    val listOfTypes : List<String> = listOf("По популярности" , "По уменьшению цены" , "По возрастанию цены"),
    val listOfTags : List<String> = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски"),
    val currentTag : String = "Смотреть все",
    val isFavorite: Boolean = false,
    val listOfProductsOriginal: List<GoodsItems> = listOf(),
    val listOfProducts: List<GoodsItems> = listOf()
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

    private val _catalogScreenUiState = MutableStateFlow(CatalogScreenUiState())
    var catalogScreenUiState: StateFlow<CatalogScreenUiState> = _catalogScreenUiState.asStateFlow()

    var catalogScreenCommodityItemsUiState: CatalogScreenCommodityItemsUiState by mutableStateOf(CatalogScreenCommodityItemsUiState.Loading)
        private set

    init {
        getCommodityItemsInfo()
//        catalogScreenUiState
//            .onEach { uiState ->
//                sortTagItems(uiState.currentTag)
//                sortItems(uiState.sortType)
//            }
//            .launchIn(viewModelScope)
    }
    /**
     * Gets Items information from the API Retrofit
     */
    private fun getCommodityItemsInfo() {
        viewModelScope.launch {
            catalogScreenCommodityItemsUiState = try {
                //Объединяем списки и создаем собственный из картинок и описания
                val listOfItems : List<CommodityItem> = productsInfoRepository.getProductsInfo().items
                val listOfImages : List<GoodsImages> = allGoodsPhotos
                val listOfProducts = mutableListOf<GoodsItems>()
                for (i in listOfItems.indices) {
                    val item = listOfItems[i]
                    val images = if (i < listOfImages.size) {
                        listOfImages[i]
                    } else {
                        GoodsImages()
                    }
                    listOfProducts.add(GoodsItems(products = item, images = images))
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
        val currentState = catalogScreenUiState.value
        _catalogScreenUiState.update {
            it.copy(
                currentTag = "",
                listOfProducts = it.listOfProductsOriginal
            )
        }
        sortItems(currentState.sortType)
    }

    private fun sortTagItems(tag : String){
        val currentState = catalogScreenUiState.value
        when (tag) {
            "Смотреть все" ->{
                _catalogScreenUiState.update {
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal
                    )
                }
                sortItems(currentState.sortType)
            }
            "Лицо" ->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.products.tags.contains("face") }
                    )
                }
                sortItems(currentState.sortType)
            }
            "Тело" ->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.products.tags.contains("body") }
                    )
                }
                sortItems(currentState.sortType)
            }
            "Загар"->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.products.tags.contains("suntan") }
                    )
                }
                sortItems(currentState.sortType)
            }
            "Маски"->{
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProductsOriginal.filter { it.products.tags.contains("mask") }
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
                            it.products.feedback?.rating ?: 0.0
                        }
                    )
                }
            }
            "По уменьшению цены" -> {
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProducts.sortedByDescending {
                            it.products.price.priceWithDiscount.toInt()
                        }
                    )
                }
            }
            "По возрастанию цены" -> {
                _catalogScreenUiState.update { it ->
                    it.copy(
                        listOfProducts = it.listOfProducts.sortedBy {
                            it.products.price.priceWithDiscount.toInt()
                        }
                    )
                }
            }
        }
    }
}