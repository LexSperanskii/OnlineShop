package com.example.onlineshop.data

import com.example.onlineshop.model.Favorite
import com.example.onlineshop.model.User

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override suspend fun countUser(name: String, lastName: String, phone: String): Int = userDao.countUser(name,lastName,phone)
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override fun getUser(): User? = userDao.getUser()

    /**
     * Для таблицы Favorite
     */
    override suspend fun insertInFavorite(id: String) = userDao.insertInFavorite(id)
    override suspend fun deleteFromFavorites(id: String) = userDao.deleteFromFavorites(id)
    override suspend fun getFavorites(): List<String> = userDao.getFavorites()

}