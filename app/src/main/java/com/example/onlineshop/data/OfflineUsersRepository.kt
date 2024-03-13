package com.example.onlineshop.data

import com.example.onlineshop.model.UserEntity
import com.example.onlineshop.model.UserModel
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    /**
     * Для таблицы users
     */
    override suspend fun insertUser(user: UserModel) = userDao.insert(user.toUserEntity())
    override suspend fun getUser(): UserModel? = userDao.getUser()?.toUserModel()
    override suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    /**
     * Для таблицы favorite
     */
    override suspend fun insertInFavorite(id: String) = userDao.insertInFavorite(id)
    override suspend fun deleteFromFavorites(id: String) = userDao.deleteFromFavorites(id)
    override fun getFavorites(): Flow<List<String>> = userDao.getFavorites()
    override suspend fun getQuantityOfFavorites(): Int = userDao.getQuantityOfFavorites()
    override suspend fun deleteAllFromFavorites() = userDao.deleteAllFromFavorites()

    private fun UserEntity.toUserModel(): UserModel {
        return UserModel(id, name, lastName, phone)
    }
    private fun UserModel.toUserEntity(): UserEntity {
        return UserEntity(id, name, lastName, phone)
    }
}