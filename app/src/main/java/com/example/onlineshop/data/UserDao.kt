package com.example.onlineshop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshop.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)
    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getUser() : UserEntity?
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()


    @Query("INSERT INTO favorite VALUES (:id)")
    suspend fun insertInFavorite(id: String)
    @Query("DELETE FROM favorite WHERE id=:id")
    suspend fun deleteFromFavorites(id: String)
    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<String>>
    @Query("SELECT COUNT(*) FROM favorite")
    suspend fun getQuantityOfFavorites(): Int
    @Query("DELETE FROM favorite")
    suspend fun deleteAllFromFavorites()
}