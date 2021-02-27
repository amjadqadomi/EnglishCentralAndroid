package com.englishcentral.githubusers.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.englishcentral.githubusers.dataclasses.GithubUser
import com.englishcentral.githubusers.database.AppDatabase
import com.englishcentral.githubusers.database.GithubUserEntity
import com.englishcentral.githubusers.interfaces.MainActivityInterfaces
import com.englishcentral.githubusers.utils.NetworkManager


class MainActivityModel(var context: Context): MainActivityInterfaces.Model {
    private var db :AppDatabase = Room.databaseBuilder(
         context,
         AppDatabase::class.java, "database-name"
     ).build()

    private var users: ArrayList<GithubUser> = ArrayList()


    private fun checkLocalDB(callback: (ArrayList<GithubUser>) -> Unit) {
        val thread = Thread {
            //fetch Records from local database
            val githubUsers = db.userDao().getAll()
            if (githubUsers.isNotEmpty()) {
                val storedGithubUsers = ArrayList<GithubUser>()
                githubUsers.forEach() {
                    val user =
                        GithubUser(it.login, it.id, it.avatar_url)
                    storedGithubUsers.add(user)
                }
                this.users.addAll(storedGithubUsers)
                callback(storedGithubUsers)
            }else {
                fetchUsersFromServer(callback)
            }
        }
        thread.start()

    }

    private fun fetchUsersFromServer(callback: (ArrayList<GithubUser>) -> Unit) {
        NetworkManager.getUsers(context, users.size) { users ->
            this.users.addAll(users)
            Log.i("response count", users.size.toString())
            users.forEach() {
                saveGithubUserToLocalDB(it)
            }
            callback(users)
        }
    }

    override fun getUsers(callback: (ArrayList<GithubUser>) -> Unit) {
        if (this.users.isEmpty()) {
            checkLocalDB(callback)
        }else {
            fetchUsersFromServer(callback)
        }
    }

    private fun saveGithubUserToLocalDB(user: GithubUser) {
        val thread = Thread {
            val id = user.id ?: return@Thread
            val userEntity = GithubUserEntity(id)
            userEntity.avatar_url = user.avatar_url
            userEntity.login = user.login
            db.userDao().insertAll(userEntity)
        }
        thread.start()

    }
}