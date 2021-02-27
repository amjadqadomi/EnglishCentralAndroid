package com.englishcentral.githubusers.interfaces

import com.englishcentral.githubusers.dataclasses.GithubUser
import com.englishcentral.githubusers.dataclasses.GithubUserDetails

interface UserDetailsInterfaces {
    interface View {
        fun updateViewData(user: GithubUserDetails)
    }

    interface Presenter {
        fun getUserDetails(username: String)
    }

    interface Model {
        fun getUserData(username: String, callback: (GithubUserDetails) -> Unit)
    }
}