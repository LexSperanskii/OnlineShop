package com.example.onlineshop.model

import com.example.onlineshop.network.CommodityDescription

/**
 * Дата Класс для отображения его во View
 */
data class CommodityItem(
    val productDescription: CommodityDescription = CommodityDescription(),
    val images: CommodityImages = CommodityImages(),
    val isFavourite: Boolean = false
)