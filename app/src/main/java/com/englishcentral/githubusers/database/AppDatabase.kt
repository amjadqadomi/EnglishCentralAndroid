package com.englishcentral.githubusers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GithubUserEntity::class, GithubUserDetailsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): GithubUserDao


    companion object {
        var TEST_MODE = false
        private val databaseName = "database_name"

        private var db: AppDatabase? = null
        private var dbInstance: GithubUserDao? = null

        fun getInstance(context: Context): GithubUserDao {
            if (dbInstance == null) {
                if (TEST_MODE) {
                    db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
                    dbInstance = db?.userDao()
                } else {
                    db = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
                    dbInstance = db?.userDao()
                }
            }
            return dbInstance!!;
        }

        private fun close() {
            db?.close()
        }
    }

}