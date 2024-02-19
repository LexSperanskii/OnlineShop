package com.example.onlineshop.di

import android.content.Context
import com.example.onlineshop.data.NetworkProductsInfoRepository
import com.example.onlineshop.data.OfflineUsersRepository
import com.example.onlineshop.data.ProductsInfoRepository
import com.example.onlineshop.data.UsersDatabase
import com.example.onlineshop.data.UsersRepository
import com.example.onlineshop.network.OnlineShopApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

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

    private val baseUrl =
        "https://run.mocky.io/v3/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
//    private val json = Json { ignoreUnknownKeys = true } //Если много ненужных ключей
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : OnlineShopApiService by lazy {
        retrofit.create(OnlineShopApiService::class.java)
    }

    override val productsInfoRepository: ProductsInfoRepository by lazy {
        NetworkProductsInfoRepository(retrofitService)
    }

    /**
     * Implementation for [UsersRepository]
     */
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(UsersDatabase.getDatabase(context).userDao()) }
}