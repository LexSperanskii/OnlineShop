package com.example.onlineshop.data

import com.example.onlineshop.model.Favorite
import com.example.onlineshop.model.User

interface UsersRepository {
    suspend fun countUser(name: String, lastName: String, phone: String): Int
    suspend fun insertUser(user: User)
    fun getUser(): User?//Flow<User?>
    suspend fun insertInFavorite(id: String)
    suspend fun deleteFromFavorites(id: String)
    suspend fun getFavorites(): List<String> //Flow<List<Favorite>>
}