package com.example.onlineshop.data

import com.example.onlineshop.model.User
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun getUser(): User? = userDao.getUser()
    override suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    /**
     * Для таблицы Favorite
     */
    override suspend fun insertInFavorite(id: String) = userDao.insertInFavorite(id)
    override suspend fun deleteFromFavorites(id: String) = userDao.deleteFromFavorites(id)
    override fun getFavorites(): Flow<List<String>> = userDao.getFavorites()
    override suspend fun getQuantityOfFavorites(): Int = userDao.getQuantityOfFavorites()
    override suspend fun deleteAllFromFavorites() = userDao.deleteAllFromFavorites()
}