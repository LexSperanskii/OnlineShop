package com.example.onlineshop.data

import com.example.onlineshop.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun insertUser(user: UserModel)
    suspend fun getUser(): UserModel?
    suspend fun deleteAllUsers()

    suspend fun insertInFavorite(id: String)
    suspend fun deleteFromFavorites(id: String)
    fun getFavorites(): Flow<List<String>>
    suspend fun getQuantityOfFavorites(): Int
    suspend fun deleteAllFromFavorites()
}