package com.englishcentral.githubusers.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GithubUserDetailsEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "login") var login: String? = "",
    @ColumnInfo(name = "avatar_url") var avatar_url: String? = "",
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "bio") var bio: String? = "",
    @ColumnInfo(name = "followers") var followers: Int? = 0,
    @ColumnInfo(name = "following") var following: Int? = 0
)