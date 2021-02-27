package com.englishcentral.githubusers.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GithubUserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "login") var login: String? = "",
    @ColumnInfo(name = "avatar_url") var avatar_url: String? = ""

)