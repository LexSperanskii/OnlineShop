package com.example.onlineshop.data

import com.example.onlineshop.network.Item
import com.example.onlineshop.network.OnlineShopApiService


interface ProductsInfoRepository {
    suspend fun getProductsInfo(): Item
}

class NetworkProductsInfoRepository(
    private val onlineShopApiService: OnlineShopApiService
) : ProductsInfoRepository {
    override suspend fun getProductsInfo(): Item = onlineShopApiService.getProductsInfo()
}