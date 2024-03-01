package com.example.onlineshop.model

import kotlinx.serialization.Serializable

@Serializable
data class Items(
    val items: List<CommodityDescription>
)
@Serializable
data class CommodityDescription(
    val available: Int = 0,
    val description: String = "",
    val feedback: Feedback? = null,
    val id: String = "",
    val info: List<Info> = listOf(),
    val ingredients: String = "",
    val price: Price = Price(),
    val subtitle: String = "",
    val tags: List<String> = listOf(),
    val title: String = ""
)
@Serializable
data class Feedback(
    val count: Int,
    val rating: Double
)
@Serializable
data class Info(
    val title: String = "",
    val value: String =  ""
)
@Serializable
data class Price(
    val discount: Int = 0,
    val price: String = "",
    val priceWithDiscount: String = "",
    val unit: String = ""
)