package com.example.onlineshop.model

import android.graphics.drawable.Drawable
import com.example.onlineshop.R

data class GoodsImages(
    val imageOne : Int,
    val imageTwo : Int
)

val allGoodsPhotos = listOf(
    GoodsImages(
        imageOne = R.drawable.one1,
        imageTwo = R.drawable.one2),
    GoodsImages(
        imageOne = R.drawable.two1,
        imageTwo = R.drawable.two2),
    GoodsImages(
        imageOne = R.drawable.three1,
        imageTwo = R.drawable.three2),
    GoodsImages(
        imageOne = R.drawable.four1,
        imageTwo = R.drawable.four2),
    GoodsImages(
        imageOne = R.drawable.five1,
        imageTwo = R.drawable.five2),
    GoodsImages(
        imageOne = R.drawable.six1,
        imageTwo = R.drawable.six2),
    GoodsImages(
        imageOne = R.drawable.seven1,
        imageTwo = R.drawable.seven2),
    GoodsImages(
        imageOne = R.drawable.eight1,
        imageTwo = R.drawable.eight2)
)