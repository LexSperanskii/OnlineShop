package com.example.onlineshop.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onlineshop.OnlineShopApplication
import com.example.onlineshop.ui.menu.catalog.CatalogScreenVIewModel
import com.example.onlineshop.ui.registrationScreen.RegistrationScreenVIewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RegistrationScreenVIewModel( onlineShopApplication().container.usersRepository )
        }
        initializer {
            CatalogScreenVIewModel(
                usersRepository = onlineShopApplication().container.usersRepository,
                productsInfoRepository = onlineShopApplication().container.productsInfoRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [OnlineShopApplication].
 */
fun CreationExtras.onlineShopApplication(): OnlineShopApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OnlineShopApplication)
