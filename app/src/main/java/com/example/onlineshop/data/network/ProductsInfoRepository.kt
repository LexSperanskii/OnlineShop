package com.example.onlineshop.data.network

import com.example.onlineshop.model.Items
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface ProductsInfoRepository {
    suspend fun getProductsInfo(): Items
}

class NetworkProductsInfoRepository() : ProductsInfoRepository {

    private val baseUrl = "https://run.mocky.io"
    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val json = Json { ignoreUnknownKeys = true } //Если много ненужных ключей
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService : OnlineShopApiService by lazy {
        retrofit.create(OnlineShopApiService::class.java)
    }
    override suspend fun getProductsInfo(): Items = retrofitService.getProductsInfo()
}