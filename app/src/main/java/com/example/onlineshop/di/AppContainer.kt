package com.example.onlineshop.di

import android.content.Context
import com.example.onlineshop.data.OfflineUsersRepository
import com.example.onlineshop.data.UsersDatabase
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.data.network.NetworkProductsInfoRepository
import com.example.onlineshop.data.network.ProductsInfoRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val usersRepository: UsersRepository
    val productsInfoRepository : ProductsInfoRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUsersRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for retrofit
     */
    override val productsInfoRepository: ProductsInfoRepository by lazy {
        NetworkProductsInfoRepository()
    }

    /**
     * Implementation for [UsersRepository]
     */
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(UsersDatabase.getDatabase(context).userDao()) }
}