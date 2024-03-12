package com.example.onlineshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlineshop.model.Favorite
import com.example.onlineshop.model.User

@Database(entities = [User::class, Favorite::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: UsersDatabase? = null
        fun getDatabase(context: Context): UsersDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, UsersDatabase::class.java,"users_database")
//                    .build()
//                    .also { Instance = it }
//            }
            //double check lock. Если несколько потоков заходят в метод getDatabase и видят Instance равным null, только один из них создаст экземпляр базы данных. Остальные будут ждать, пока первый поток завершит инициализацию и присвоение Instance.
            return Instance ?: synchronized(this) {
                Instance ?: Room.databaseBuilder(context, UsersDatabase::class.java,"users_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}