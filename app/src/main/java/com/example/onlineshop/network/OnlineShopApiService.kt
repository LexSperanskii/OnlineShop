package com.example.onlineshop.network

import retrofit2.http.GET

interface OnlineShopApiService {
    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProductsInfo(): Item
}