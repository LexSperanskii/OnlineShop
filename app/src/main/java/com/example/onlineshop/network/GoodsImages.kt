package com.example.onlineshop.network

import com.example.onlineshop.R

data class GoodsImages(
    val images: List<Int>
)

val allGoodsPhotos = listOf(
    GoodsImages(
        images = listOf(R.drawable.one1, R.drawable.one2)
    ),
    GoodsImages(
        images = listOf(R.drawable.two1, R.drawable.two2)
    ),
    GoodsImages(
        images = listOf(R.drawable.three1, R.drawable.three2)
    ),
    GoodsImages(
        images = listOf(R.drawable.four1, R.drawable.four2)
    ),
    GoodsImages(
        images = listOf(R.drawable.five1, R.drawable.five2)
    ),
    GoodsImages(
        images = listOf(R.drawable.six1, R.drawable.six2)
    ),
    GoodsImages(
        images = listOf(R.drawable.seven1, R.drawable.seven2)
    ),
    GoodsImages(
        images = listOf(R.drawable.eight1, R.drawable.eight2)
    )
)

//data class GoodsImages(
//    val imageOne : Int,
//    val imageTwo : Int
//)
//
//val allGoodsPhotos = listOf(
//    GoodsImages(
//        imageOne = R.drawable.one1,
//        imageTwo = R.drawable.one2),
//    GoodsImages(
//        imageOne = R.drawable.two1,
//        imageTwo = R.drawable.two2),
//    GoodsImages(
//        imageOne = R.drawable.three1,
//        imageTwo = R.drawable.three2),
//    GoodsImages(
//        imageOne = R.drawable.four1,
//        imageTwo = R.drawable.four2),
//    GoodsImages(
//        imageOne = R.drawable.five1,
//        imageTwo = R.drawable.five2),
//    GoodsImages(
//        imageOne = R.drawable.six1,
//        imageTwo = R.drawable.six2),
//    GoodsImages(
//        imageOne = R.drawable.seven1,
//        imageTwo = R.drawable.seven2),
//    GoodsImages(
//        imageOne = R.drawable.eight1,
//        imageTwo = R.drawable.eight2)
//)