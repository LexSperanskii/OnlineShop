package com.example.onlineshop.ui.screens.favoriteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.data.network.ProductsInfoRepository
import com.example.onlineshop.model.CommodityDescription
import com.example.onlineshop.model.CommodityImages
import com.example.onlineshop.model.CommodityItem
import com.example.onlineshop.model.allCommodityPhotos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface FavoriteScreenNetworkUiState {
    object Success : FavoriteScreenNetworkUiState
    object Error : FavoriteScreenNetworkUiState
    object Loading : FavoriteScreenNetworkUiState
}
data class FavoriteScreenUiState(
    val listOfProducts: List<CommodityItem> = listOf()
)

class FavoriteScreenViewModel(
    private val usersRepository: UsersRepository,
    private val productsInfoRepository: ProductsInfoRepository
) : ViewModel() {

    /**
     * State для состояния Сети
     */
    var favoriteScreenNetworkUiState: FavoriteScreenNetworkUiState by mutableStateOf(
        FavoriteScreenNetworkUiState.Loading
    )
        private set

    /**
     * State для экарана с Избранным
     */
    private val _favoriteScreenUiState = MutableStateFlow(FavoriteScreenUiState())
    val favoriteScreenUiState: StateFlow<FavoriteScreenUiState> = _favoriteScreenUiState
        .combine(usersRepository.getFavorites()) { localState, favorite ->
            localState.copy(
                listOfProducts = localState.listOfProducts.filter { item ->
                    item.productDescription.id in favorite
                }.map { item ->
                    // Создаем копию элемента с обновленным полем isFavourite
                    item.copy(isFavourite = true)
                }
            )
        } //для преобразования Flow в StateFlow.
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000), //Преобразование будет запущено, когда есть хотя бы один подписчик на StateFlow, и будет продолжаться до тех пор, пока есть подписчики. Время жизни этой операции составляет 5 секунд (5000 миллисекунд
            initialValue = FavoriteScreenUiState()
        )

    init {
        getCommodityItemsInfo()
    }
    /**
     * Gets Items information from the API Retrofit
     */
    fun getCommodityItemsInfo() {
        viewModelScope.launch {
            favoriteScreenNetworkUiState = try {
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
                _favoriteScreenUiState.update {
                    it.copy(
                        listOfProducts = listOfProducts
                    )
                }
                //состояние загрузки
                FavoriteScreenNetworkUiState.Success
            } catch (e: IOException) {
                FavoriteScreenNetworkUiState.Error
            }
        }
    }
    fun deleteFromFavorites(commodityItem: CommodityItem){
        viewModelScope.launch {
            usersRepository.deleteFromFavorites(commodityItem.productDescription.id)
        }
    }

}

