package com.example.onlineshop.data

import com.example.onlineshop.model.User
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override suspend fun countUser(name: String, lastName: String, phone: String): Int = userDao.countUser(name,lastName,phone)
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override fun getUserStream(): Flow<User?> = userDao.getUser()
}