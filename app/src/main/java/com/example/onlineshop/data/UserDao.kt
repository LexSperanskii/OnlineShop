package com.example.onlineshop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshop.model.Favorite
import com.example.onlineshop.model.User

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) FROM users WHERE name = :name AND lastName = :lastName AND phone = :phone")
    suspend fun countUser(name: String, lastName: String, phone: String): Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    fun getUser() : User? //Flow<User>

    @Query("INSERT INTO favorite VALUES (:id)")
    suspend fun insertInFavorite(id: String)
    @Query("DELETE FROM favorite WHERE id=:id")
    suspend fun deleteFromFavorites(id: String)
    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<String>  //Flow<List<Favorite>>
}