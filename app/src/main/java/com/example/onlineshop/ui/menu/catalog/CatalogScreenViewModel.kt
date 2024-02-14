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
    val listOfTypes : List<String> = listOf("По популярности" , "По уменьшению цены" , "По возрастанию цены")

)
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
}