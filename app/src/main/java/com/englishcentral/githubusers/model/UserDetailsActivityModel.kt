package com.englishcentral.githubusers.model

import android.content.Context
import androidx.room.Room
import com.englishcentral.githubusers.database.AppDatabase
import com.englishcentral.githubusers.database.GithubUserDetailsEntity
import com.englishcentral.githubusers.dataclasses.GithubUserDetails
import com.englishcentral.githubusers.interfaces.UserDetailsInterfaces
import com.englishcentral.githubusers.utils.NetworkManager

class UserDetailsActivityModel(var context: Context) : UserDetailsInterfaces.Model {


    override fun getUserData(username: String, callback: (GithubUserDetails) -> Unit) {

        val thread = Thread {
            //fetch Records from local database
            val githubUserDetailsEntity = AppDatabase.getInstance(context).findByUsername(username)
            if (githubUserDetailsEntity == null) {
                fetchUsersFromServer(username, callback)
            }else {
                val githubUserDetails = GithubUserDetails(githubUserDetailsEntity.login, githubUserDetailsEntity.id, githubUserDetailsEntity.avatar_url,
                    githubUserDetailsEntity.name, githubUserDetailsEntity.bio, githubUserDetailsEntity.followers, githubUserDetailsEntity.following)
                callback(githubUserDetails)
                fetchUsersFromServer(username, callback)
            }
        }
        thread.start()
    }

    private fun fetchUsersFromServer(username: String, callback: (GithubUserDetails) -> Unit) {
        NetworkManager.getUserDetails(context, username) { userDetails ->
            saveGithubUserDetailsToLocalDB(userDetails)
            callback(userDetails)
        }
    }

    private fun saveGithubUserDetailsToLocalDB(userDetails: GithubUserDetails) {
        val thread = Thread {
            val id = userDetails.id ?: return@Thread
            val userDetailsEntity = GithubUserDetailsEntity(id)
            userDetailsEntity.avatar_url = userDetails.avatar_url
            userDetailsEntity.login = userDetails.login
            userDetailsEntity.followers = userDetails.followers
            userDetailsEntity.following = userDetails.following
            userDetailsEntity.bio = userDetails.bio
            AppDatabase.getInstance(context).insertAll(userDetailsEntity)
        }
        thread.start()

    }

}