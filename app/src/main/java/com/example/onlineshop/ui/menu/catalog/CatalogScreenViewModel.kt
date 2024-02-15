package com.example.onlineshop.ui.menu.catalog

import androidx.lifecycle.ViewModel
import com.example.onlineshop.data.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CatalogScreenSortingRowUiState(
    val isExpanded : Boolean = false,
    val sortType : String = "По популярности",
    val listOfTypes : List<String> = listOf("По популярности" , "По уменьшению цены" , "По возрастанию цены"),
    val listOfTags : List<String> = listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски"),
    val currentTag : String = "Смотреть все"
)
sealed interface ScreenUiState {
    data class Success(val covers: List<String>) : ScreenUiState
    object Error : ScreenUiState
    object Loading : ScreenUiState
}
sealed class ScreenUiState2{
    data class Success(val covers: List<String>) : ScreenUiState2()
    object Error : ScreenUiState2()
    object Loading : ScreenUiState2()
}


class CatalogScreenVIewModel(private val usersRepository: UsersRepository): ViewModel() {

    private val _sortingRowUiState = MutableStateFlow(CatalogScreenSortingRowUiState())
    var sortingRowUiState: StateFlow<CatalogScreenSortingRowUiState> = _sortingRowUiState.asStateFlow()

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