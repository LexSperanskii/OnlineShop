package com.example.onlineshop.data

import com.example.onlineshop.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun insertUser(user: User)
    suspend fun getUser(): User?
    suspend fun deleteAllUsers()

    suspend fun insertInFavorite(id: String)
    suspend fun deleteFromFavorites(id: String)
    fun getFavorites(): Flow<List<String>>
    suspend fun getQuantityOfFavorites(): Int
    suspend fun deleteAllFromFavorites()
}