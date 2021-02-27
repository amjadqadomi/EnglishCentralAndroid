package com.englishcentral.githubusers.utils

interface GlobalKeys {
    companion object {
        //shared preferences keys
        val accessTokenKey = "access_token"
        val sharedPreferencesName: String = "shared_preferences"


        //URLs
        const val baseURL = "https://api.github.com/users"



    }
}