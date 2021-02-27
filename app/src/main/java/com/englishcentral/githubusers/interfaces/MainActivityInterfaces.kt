package com.englishcentral.githubusers.interfaces

import com.englishcentral.githubusers.dataclasses.GithubUser

interface MainActivityInterfaces {

    interface View {
        fun updateViewData(users: ArrayList<GithubUser>)
    }

    interface Presenter {
        fun getUsers()
    }

    interface Model {
        fun getUsers(callback: (ArrayList<GithubUser>) -> Unit)
    }

}