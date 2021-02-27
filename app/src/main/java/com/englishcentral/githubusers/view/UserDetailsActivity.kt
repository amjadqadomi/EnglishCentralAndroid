package com.englishcentral.githubusers.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.englishcentral.githubusers.R
import com.englishcentral.githubusers.dataclasses.GithubUser
import com.englishcentral.githubusers.dataclasses.GithubUserDetails
import com.englishcentral.githubusers.interfaces.MainActivityInterfaces
import com.englishcentral.githubusers.interfaces.UserDetailsInterfaces
import com.englishcentral.githubusers.presenter.MainActivityPresenter
import com.englishcentral.githubusers.presenter.UserDetailsActivityPresenter
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity(), UserDetailsInterfaces.View {
    private var presenter: UserDetailsActivityPresenter? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var avatarImageView: CircleImageView
    private lateinit var nameTextView: TextView
    private lateinit var bioTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var followersTextView: TextView
    private lateinit var followingTextView: TextView

    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra("EXTRA_USERNAME")
        setContentView(R.layout.activity_user_details)
        presenter = UserDetailsActivityPresenter(this, this)
        initView()
    }

    private fun initView() {
        progressBar = findViewById(R.id.progress_bar)
        avatarImageView = findViewById(R.id.avatar_image_view)
        nameTextView = findViewById(R.id.name_text_view)
        bioTextView = findViewById(R.id.bio_text_view)
        usernameTextView = findViewById(R.id.username_text_view)
        followersTextView = findViewById(R.id.followers_text_view)
        followingTextView = findViewById(R.id.following_text_view)

        fetchUserDetails()
    }

    private fun fetchUserDetails() {
        progressBar.visibility = View.VISIBLE
        val username = username ?: return
        presenter?.getUserDetails(username)
    }

    override fun updateViewData(user: GithubUserDetails) {
        runOnUiThread {
            progressBar.visibility = View.GONE
            Glide.with(this).load(user.avatar_url).into(avatarImageView)
            nameTextView.text = user.name
            bioTextView.text = user.bio
            usernameTextView.text = user.login
            followersTextView.text =
                String.format(getString(R.string.followers_text_view), user.followers)
            followingTextView.text =
                String.format(getString(R.string.following_text_view), user.following)

            if (user.name == null || user.name!!.isEmpty()) {
                bioTextView.visibility = View.GONE
            }
        }
    }

}