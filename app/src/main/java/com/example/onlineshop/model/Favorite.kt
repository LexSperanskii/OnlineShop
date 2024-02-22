package com.example.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onlineshop.network.CommodityDescription

@Entity(tableName = "favorite")
data class  Favorite(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
)