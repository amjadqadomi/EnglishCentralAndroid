package com.englishcentral.githubusers.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GithubUserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): GithubUserDao
}