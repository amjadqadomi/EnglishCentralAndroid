package com.englishcentral.githubusers.presenter

import android.content.Context
import com.englishcentral.githubusers.interfaces.MainActivityInterfaces
import com.englishcentral.githubusers.interfaces.UserDetailsInterfaces
import com.englishcentral.githubusers.model.MainActivityModel
import com.englishcentral.githubusers.model.UserDetailsActivityModel


class UserDetailsActivityPresenter(_view: UserDetailsInterfaces.View, context: Context): UserDetailsInterfaces.Presenter {

    private var view: UserDetailsInterfaces.View = _view
    private var model: UserDetailsInterfaces.Model = UserDetailsActivityModel(context)

    override fun getUserDetails(username: String) {
        model.getUserData(username) { user ->
            view.updateViewData(user)
        }
    }

}