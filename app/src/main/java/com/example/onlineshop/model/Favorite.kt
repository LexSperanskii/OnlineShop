package com.example.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Абстрактная модель данных для избранных элементов
data class FavoriteModel(
    val id: String = "",
)
@Entity(tableName = "favorite")
data class  Favorite(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
)