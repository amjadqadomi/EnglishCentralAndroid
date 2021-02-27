package com.englishcentral.githubusers.interfaces

import com.englishcentral.githubusers.dataclasses.GithubUser

interface ContractInterface {

    interface View {
        fun initView()
        fun updateViewData(users: ArrayList<GithubUser>)
    }

    interface Presenter {
        fun getUsers()
    }

    interface Model {
        fun getUsers(callback: (ArrayList<GithubUser>) -> Unit)
    }

}