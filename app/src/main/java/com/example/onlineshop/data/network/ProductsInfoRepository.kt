package com.example.onlineshop.data.network

import com.example.onlineshop.model.Items


interface ProductsInfoRepository {
    suspend fun getProductsInfo(): Items
}

class NetworkProductsInfoRepository(
    private val onlineShopApiService: OnlineShopApiService
) : ProductsInfoRepository {
    override suspend fun getProductsInfo(): Items = onlineShopApiService.getProductsInfo()
}