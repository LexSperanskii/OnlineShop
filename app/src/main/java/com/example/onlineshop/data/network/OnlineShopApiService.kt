package com.example.onlineshop.data.network

import com.example.onlineshop.model.Items
import retrofit2.http.GET
interface OnlineShopApiService {
    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProductsInfo(): Items
}