package com.example.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey
// Абстрактная модель данных для пользователя
data class UserModel(
    val id: Int = 0,
    val name: String,
    val lastName: String,
    val phone: String,
)
@Entity(tableName = "users")
data class  User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val lastName: String,
    val phone: String,
)