package com.example.onlineshop.network

import kotlinx.serialization.Serializable

@Serializable
data class Items(
    val items: List<CommodityItem>
)
@Serializable
data class CommodityItem(
    val available: Int,
    val description: String,
    val feedback: Feedback?,
    val id: String,
    val info: List<Info>,
    val ingredients: String,
    val price: Price,
    val subtitle: String,
    val tags: List<String>,
    val title: String
)
@Serializable
data class Feedback(
    val count: Int,
    val rating: Double
)
@Serializable
data class Info(
    val title: String,
    val value: String
)
@Serializable
data class Price(
    val discount: Int,
    val price: String,
    val priceWithDiscount: String,
    val unit: String
)