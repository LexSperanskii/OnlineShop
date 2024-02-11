package com.example.onlineshop.ui.registrationScreen

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegistrationScreenUiState(
    val name : String = "",
    val nameIsError : Boolean = false,
    val lastName : String = "",
    val lastNameIsError : Boolean = false,
    val number : String = "",
    val numberIsError : Boolean = false
)
class RegistrationScreenVIewModel(): ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationScreenUiState())
    var uiState : StateFlow<RegistrationScreenUiState> = _uiState.asStateFlow()

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
        \\d{1,10} проверяет, что после "+7" идет от 1 до 11 цифр.
        $ конец строки.
        */
        if (correctNumber.matches(Regex("^\\+7\\d{0,10}$"))){
            Log.d("testlog",correctNumber)
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
    fun formatPhoneNumber(phoneNumber: String): String {
        return when (phoneNumber.length){
            2 -> {
                "${phoneNumber[0]}${phoneNumber[1]} xxx xxx-xx-xx"
            }

            3 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}xx xxx-xx-xx"
            }

            4 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}x xxx-xx-xx"
            }

            5 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} xxx-xx-xx"
            }

            6 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}xx-xx-xx"
            }

            7 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}x-xx-xx"
            }

            8 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-xx-xx"
            }

            9 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}x-xx"
            }

            10 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}${phoneNumber[9]}-xx"
            }

            11 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}${phoneNumber[9]}-${phoneNumber[10]}x"
            }

            12 -> {
                "${phoneNumber[0]}${phoneNumber[1]} ${phoneNumber[2]}${phoneNumber[3]}${phoneNumber[4]} ${phoneNumber[5]}${phoneNumber[6]}${phoneNumber[7]}-${phoneNumber[8]}${phoneNumber[9]}-${phoneNumber[10]}${phoneNumber[11]}"
            }

            else -> {
                phoneNumber
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
    fun onEraseNumberClick(){
        _uiState.update {
            it.copy(
                number = "",
                numberIsError = false
            )
        }
    }
}