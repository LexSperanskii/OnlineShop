package com.example.onlineshop.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.onlineshop.OnlineShopApplication
import com.example.onlineshop.ui.screens.accountScreen.AccountScreenViewModel
import com.example.onlineshop.ui.screens.catalogAndProductScreen.CatalogProductScreenViewModel
import com.example.onlineshop.ui.screens.favoriteScreen.FavoriteScreenViewModel
import com.example.onlineshop.ui.screens.registrationScreen.RegistrationScreenVIewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RegistrationScreenVIewModel( onlineShopApplication().container.usersRepository )
        }
        initializer {
            CatalogProductScreenViewModel(
                usersRepository = onlineShopApplication().container.usersRepository,
                productsInfoRepository = onlineShopApplication().container.productsInfoRepository
            )
        }
        initializer {
            AccountScreenViewModel(
                usersRepository = onlineShopApplication().container.usersRepository
            )
        }
        initializer {
            FavoriteScreenViewModel(
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
