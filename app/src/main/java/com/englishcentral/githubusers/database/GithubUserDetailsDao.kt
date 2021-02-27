package com.englishcentral.githubusers.database

import androidx.room.*

@Dao
interface GithubUserDetailsDao {
    @Query("SELECT * FROM GithubUserDetailsEntity")
    fun getAll(): List<GithubUserDetailsEntity>
//
    @Query("SELECT * FROM GithubUserDetailsEntity WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<GithubUserDetailsEntity>

    @Query("SELECT * FROM GithubUserDetailsEntity WHERE login LIKE :username LIMIT 1")
    fun findByUsername(username: String): GithubUserDetailsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: GithubUserDetailsEntity)

    @Delete
    fun delete(user: GithubUserDetailsEntity)
}