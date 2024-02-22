package com.example.onlineshop.model

import com.example.onlineshop.R


data class CommodityImages(
    val listOfImage: List<Int> = listOf(R.drawable.one1, R.drawable.one2)
)

val allCommodityPhotos = listOf(
    CommodityImages(
        listOfImage = listOf(R.drawable.one1, R.drawable.one2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.two1, R.drawable.two2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.three1, R.drawable.three2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.four1, R.drawable.four2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.five1, R.drawable.five2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.six1, R.drawable.six2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.seven1, R.drawable.seven2)
    ),
    CommodityImages(
        listOfImage = listOf(R.drawable.eight1, R.drawable.eight2)
    )
)