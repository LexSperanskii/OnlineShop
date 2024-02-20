package com.example.onlineshop.data

import com.example.onlineshop.network.CommodityItem
import com.example.onlineshop.network.Items
import com.example.onlineshop.network.OnlineShopApiService


interface ProductsInfoRepository {
    suspend fun getProductsInfo(): Items
}

class NetworkProductsInfoRepository(
    private val onlineShopApiService: OnlineShopApiService
) : ProductsInfoRepository {
    override suspend fun getProductsInfo(): Items = onlineShopApiService.getProductsInfo()
}