package com.englishcentral.githubusers.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.englishcentral.githubusers.dataclasses.GithubUser
import com.englishcentral.githubusers.database.AppDatabase
import com.englishcentral.githubusers.database.GithubUserEntity
import com.englishcentral.githubusers.interfaces.ContractInterface
import com.englishcentral.githubusers.utils.NetworkManager


class MainActivityModel(var context: Context): ContractInterface.Model {
    lateinit var db :AppDatabase

    init {
       db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()

    }

    private var users: ArrayList<GithubUser> = ArrayList()


    private fun checkLocalDB(callback: (ArrayList<GithubUser>) -> Unit) {
        val thread = Thread {

            //fetch Records from local database
            val githubUsers = db.userDao().getAll()
            if (githubUsers.isNotEmpty()) {
                val arrayLisssss = ArrayList<GithubUser>()
                githubUsers.forEach() {
                    val user =
                        GithubUser(
                            it.login,
                            it.id,
                            it.node_id,
                            it.avatar_url,
                            it.gravatar_id,
                            it.url,
                            it.html_url,
                            it.followers_url,
                            it.following_url,
                            it.gists_url,
                            it.starred_url,
                            it.subscriptions_url,
                            it.organizations_url,
                            it.repos_url,
                            it.events_url,
                            it.received_events_url,
                            it.type,
                            it.site_admin
                        )
                    arrayLisssss.add(user)
                }
                this.users.addAll(arrayLisssss)
                callback(arrayLisssss)
            }else {
                fetchUsersFromServer(callback)
            }
        }
        thread.start()

    }

    private fun fetchUsersFromServer(callback: (ArrayList<GithubUser>) -> Unit) {
        NetworkManager.getAllProducts(context, users.size) { users ->
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
            val bookEntity = GithubUserEntity(id)
            bookEntity.node_id = user.node_id
            bookEntity.avatar_url = user.avatar_url
            bookEntity.gravatar_id = user.gravatar_id
            bookEntity.url = user.url
            bookEntity.html_url = user.html_url
            bookEntity.followers_url = user.followers_url
            bookEntity.following_url = user.following_url
            bookEntity.gists_url = user.gists_url
            bookEntity.starred_url = user.starred_url
            bookEntity.subscriptions_url = user.subscriptions_url
            bookEntity.organizations_url = user.organizations_url
            bookEntity.repos_url = user.repos_url
            bookEntity.events_url = user.events_url
            bookEntity.received_events_url = user.received_events_url
            bookEntity.type = user.type
            bookEntity.login = user.login
            bookEntity.site_admin = user.site_admin

            db.userDao().insertAll(bookEntity)

        }
        thread.start()

    }
}