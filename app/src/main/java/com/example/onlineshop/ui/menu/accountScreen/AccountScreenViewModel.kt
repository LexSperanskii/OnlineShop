package com.example.onlineshop.ui.menu.accountScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AccountScreenUiState(
    val accountName : String = "",
    val accountSurname : String = "",
    val accountPhoneNumber : String = "",
    val favoritesQuantity : Int = 0
)
class AccountScreenViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountScreenUiState())
    var uiState : StateFlow<AccountScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            val quantity = usersRepository.getQuantityOfFavorites()
            val user = usersRepository.getUser()
            if (user != null)
                _uiState.update {
                    it.copy(
                        accountName = user.name,
                        accountSurname = user.lastName,
                        accountPhoneNumber = user.phone,
                        favoritesQuantity = quantity
                    )
                }
        }
    }

    fun deleteUserAndFavorites(){
        viewModelScope.launch(Dispatchers.IO){
            usersRepository.deleteAllFromFavorites()
            usersRepository.deleteAllUsers()
        }
    }
}