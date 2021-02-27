package com.englishcentral.githubusers.database

import androidx.room.*

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM GithubUserEntity")
    fun getAll(): List<GithubUserEntity>

    @Query("SELECT * FROM GithubUserEntity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<GithubUserEntity>

    @Query("SELECT * FROM GithubUserEntity WHERE login LIKE :loginName LIMIT 1")
    fun findByName(loginName: String): GithubUserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: GithubUserEntity)

    @Delete
    fun delete(user: GithubUserEntity)
}
