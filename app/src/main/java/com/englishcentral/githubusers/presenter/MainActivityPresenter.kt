package com.englishcentral.githubusers.presenter

import android.content.Context
import com.englishcentral.githubusers.interfaces.ContractInterface
import com.englishcentral.githubusers.model.MainActivityModel

class MainActivityPresenter(_view: ContractInterface.View, context: Context): ContractInterface.Presenter {

    private var view: ContractInterface.View = _view
    private var model: ContractInterface.Model = MainActivityModel(context)

    init {
//        view.initView()
    }


    override fun getUsers() {
       model.getUsers() { users ->
        view.updateViewData(users)
       }
    }

}