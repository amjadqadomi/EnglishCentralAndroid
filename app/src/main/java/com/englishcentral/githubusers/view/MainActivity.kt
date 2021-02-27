package com.englishcentral.githubusers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.englishcentral.githubusers.dataclasses.GithubUser
import com.englishcentral.githubusers.R
import com.englishcentral.githubusers.interfaces.ContractInterface
import com.englishcentral.githubusers.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), ContractInterface.View, UsersListCallback {
    private var presenter: MainActivityPresenter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var customerAdapter: UsersCustomAdapter
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this,this)
        initView()
    }


    override fun initView() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.users_list_view)
        recyclerView.layoutManager = linearLayoutManager

        progressBar = findViewById(R.id.progress_bar)

        customerAdapter = UsersCustomAdapter(this, ArrayList())
        customerAdapter.callback = this
        recyclerView.adapter = customerAdapter

        getMoreUsers()

    }

    override fun updateViewData(users: ArrayList<GithubUser>) {
        runOnUiThread {
            progressBar.visibility = View.GONE
            customerAdapter.addToDataSet(users)
        }
    }

    override fun didSelectUser(user: GithubUser) {

    }

    override fun getMoreUsers() {
        progressBar.visibility = View.VISIBLE
        presenter?.getUsers()
    }
}