package com.example.onlineshop.ui.menu.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.ProductsInfoRepository
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.network.CommodityItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class CatalogScreenSortingRowUiState(
    val isExpanded : Boolean = false,
    val sortType : String = "По популярности",
    val listOfTypes : List<String> = listOf("По популярности" , "По уменьшению цены" , "По возрастанию цены"),
    val listOfTags : List<String> = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски"),
    val currentTag : String = "Смотреть все"
)

sealed interface CatalogScreenCommodityItemsUiState {
    data class Success(
        val isFavorite: Boolean = false,
        val listOfItems: List<CommodityItem> = listOf(),
    ) : CatalogScreenCommodityItemsUiState

    object Error : CatalogScreenCommodityItemsUiState
    object Loading : CatalogScreenCommodityItemsUiState
}


class CatalogScreenVIewModel(
    private val usersRepository: UsersRepository,
    private val productsInfoRepository: ProductsInfoRepository
) : ViewModel() {

    private val _sortingRowUiState = MutableStateFlow(CatalogScreenSortingRowUiState())
    var sortingRowUiState: StateFlow<CatalogScreenSortingRowUiState> = _sortingRowUiState.asStateFlow()

    var catalogScreenCommodityItemsUiState: CatalogScreenCommodityItemsUiState by mutableStateOf(CatalogScreenCommodityItemsUiState.Loading)
        private set

    init {
        getCommodityItemsInfo()
    }
    /**
     * Gets Items information from the API Retrofit
     */
    fun getCommodityItemsInfo() {
        viewModelScope.launch {
            catalogScreenCommodityItemsUiState = try {
                CatalogScreenCommodityItemsUiState.Success(listOfItems = productsInfoRepository.getProductsInfo().items)
            } catch (e: IOException) {
                CatalogScreenCommodityItemsUiState.Error
            }
        }
    }
    fun expandChange(){
        _sortingRowUiState.update {
            it.copy(
                isExpanded = !it.isExpanded
            )
        }
    }
    fun onDropdownMenuItemClick(sortType: String){
        _sortingRowUiState.update {
            it.copy(
                sortType = sortType,
                isExpanded = !it.isExpanded
            )
        }
    }
    fun onTagClick(tag : String){
        _sortingRowUiState.update {
            it.copy(
                currentTag = tag
            )
        }
    }
    fun onEraseTagClick(){
        _sortingRowUiState.update {
            it.copy(
                currentTag = ""
            )
        }
    }
}