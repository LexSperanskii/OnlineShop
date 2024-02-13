package com.example.onlineshop.data

import com.example.onlineshop.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun countUser(name: String, lastName: String, phone: String): Int
    suspend fun insertUser(user: User)
    fun getUser(): User?//Flow<User?>
}