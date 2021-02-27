package com.englishcentral.githubusers.database

import androidx.room.*
import com.englishcentral.githubusers.dataclasses.GithubUser

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

    @Query("SELECT * FROM GithubUserEntity WHERE id LIKE :id LIMIT 1")
    fun getGithubUser(id:Int): GithubUserEntity



    @Query("SELECT * FROM GithubUserDetailsEntity")
    fun getAllDetails(): List<GithubUserDetailsEntity>

    @Query("SELECT * FROM GithubUserDetailsEntity WHERE login LIKE :username LIMIT 1")
    fun findByUsername(username: String): GithubUserDetailsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: GithubUserDetailsEntity)

    @Query("SELECT * FROM GithubUserDetailsEntity WHERE id LIKE :id LIMIT 1")
    fun getGithubUserDetails(id:Int): GithubUserDetailsEntity


    @Delete
    fun delete(user: GithubUserDetailsEntity)


}
