package com.example.onlineshop.ui.menu.AccountScreen

import androidx.lifecycle.ViewModel
import com.example.onlineshop.data.ProductsInfoRepository
import com.example.onlineshop.data.UsersRepository

class CatalogProductScreenViewModel(
    private val usersRepository: UsersRepository,
    private val productsInfoRepository: ProductsInfoRepository
) : ViewModel() {