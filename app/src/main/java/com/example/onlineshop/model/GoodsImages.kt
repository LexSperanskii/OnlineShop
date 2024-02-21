package com.example.onlineshop.model

import com.example.onlineshop.R
import com.example.onlineshop.network.CommodityItem

data class GoodsItems(
    val products: CommodityItem,
    val images: GoodsImages
)
data class GoodsImages(
    val listOfImage: List<Int> = listOf(R.drawable.one1, R.drawable.one2)
)

val allGoodsPhotos = listOf(
    GoodsImages(
        listOfImage = listOf(R.drawable.one1, R.drawable.one2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.two1, R.drawable.two2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.three1, R.drawable.three2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.four1, R.drawable.four2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.five1, R.drawable.five2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.six1, R.drawable.six2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.seven1, R.drawable.seven2)
    ),
    GoodsImages(
        listOfImage = listOf(R.drawable.eight1, R.drawable.eight2)
    )
)