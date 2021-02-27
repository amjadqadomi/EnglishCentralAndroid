package com.englishcentral.githubusers.presenter

import android.content.Context
import com.englishcentral.githubusers.interfaces.MainActivityInterfaces
import com.englishcentral.githubusers.model.MainActivityModel

class MainActivityPresenter(_view: MainActivityInterfaces.View, context: Context): MainActivityInterfaces.Presenter {

    private var view: MainActivityInterfaces.View = _view
    private var model: MainActivityInterfaces.Model = MainActivityModel(context)


    override fun getUsers() {
       model.getUsers() { users ->
        view.updateViewData(users)
       }
    }

}