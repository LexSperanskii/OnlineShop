package com.example.onlineshop.ui.screens.registrationScreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistrationScreenUiState(
    val id: Int = 0,
    val name : String = "",
    val nameIsError : Boolean = false,
    val lastName : String = "",
    val lastNameIsError : Boolean = false,
    val number : String = "",
    val numberIsError : Boolean = false
)

class RegistrationScreenVIewModel(private val usersRepository: UsersRepository): ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationScreenUiState())
    val uiState : StateFlow<RegistrationScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = usersRepository.getUser()
            _uiState.update {
                it.copy(
                    id = user?.id ?: 0,
                    name = user?.name ?: "",
                    lastName = user?.lastName ?: "",
                    number = user?.phone?: ""
                )
            }
        }
    }
    fun updateNameField(name: String){
        if (name.all { it.isLetter() && it.code in 0x0400..0x04FF}){
            _uiState.update {
                it.copy(
                    name = name,
                    nameIsError = false
                )
            }
        }else{
            _uiState.update {
                it.copy(
                    name = name,
                    nameIsError = true
                )
            }
        }
    }
    fun updateLastNameField(name: String){
        if (name.all { it.isLetter() && it.code in 0x0400..0x04FF}){
            _uiState.update {
                it.copy(
                    lastName = name,
                    lastNameIsError = false
                )
            }
        }else{
            _uiState.update {
                it.copy(
                    lastName = name,
                    lastNameIsError = true
                )
            }
        }
    }
    fun updateNumberField(number: String) {

        var correctNumber = ""
        val region = "+7"

        if (number == "7"){
            _uiState.update {
                it.copy(
                    number = region,
                )
            }
            isNumberValid(number)
        }
        if (uiState.value.number.isEmpty() && number.isDigitsOnly()) {
            _uiState.update {
                it.copy(
                    number = region + number,
                )
            }
            isNumberValid(number)
        }
        if (uiState.value.number.isNotEmpty()){
            val digital = number.filter { it.isDigit() }
            correctNumber = "+$digital"
        }
        /*
        Регулярное выражение
        ^ начало строки
        \\+7 проверяет, что строка начинается с символов "+7".
        \\d{1,10} проверяет, что после "+7" идет от 1 до 10 цифр.
        $ конец строки.
        */
        if (correctNumber.matches(Regex("^\\+7\\d{0,10}$"))){
            _uiState.update {
                it.copy(
                    number = correctNumber,
                )
            }
            isNumberValid(correctNumber)
        }
    }
    private fun isNumberValid(number : String){
        if (number.matches(Regex("^\\+7\\d{10}$"))){
            _uiState.update {
                it.copy(
                    numberIsError = false
                )
            }
        }else{
            _uiState.update {
                it.copy(
                    numberIsError = true
                )
            }
        }
    }

    fun onEraseNameClick(){
        _uiState.update {
            it.copy(
                name = "",
                nameIsError = false
            )
        }
    }
    fun onEraseLastNameClick(){
        _uiState.update {
            it.copy(
                lastName = "",
                lastNameIsError = false
            )
        }
    }
    fun onEraseNumberClick() {
        _uiState.update {
            it.copy(
                number = "",
                numberIsError = false
            )
        }
    }
    fun saveUser() {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.insertUser(uiState.value.toUser())
        }
    }
}

fun RegistrationScreenUiState.toUser(): User = User(
    name = name,
    lastName = lastName,
    phone = number
)