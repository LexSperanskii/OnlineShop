package com.example.onlineshop.ui.registrationScreen

import androidx.lifecycle.ViewModel

data class RegistrationScreenUiState(
    val searchField : String = "",
    val tableName : String = "",
)
class RegistrationScreenVIewModel(): ViewModel() {
}