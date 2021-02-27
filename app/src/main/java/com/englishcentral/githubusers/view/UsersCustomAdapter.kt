package com.englishcentral.githubusers.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.englishcentral.githubusers.dataclasses.GithubUser
import com.englishcentral.githubusers.R

interface UsersListCallback {
    fun didSelectUser(user: GithubUser)
    fun getMoreUsers()
}

class UsersCustomAdapter(private val context: Context, private var dataSet: ArrayList<GithubUser>) : RecyclerView.Adapter<UsersCustomAdapter.ViewHolder>(),
    View.OnClickListener {

    lateinit var callback: UsersListCallback

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name_text_view)
        val avatarImageView: ImageView = view.findViewById(R.id.avatar_image_view)
        
    }

    fun updateDataSet(dataSet: ArrayList<GithubUser>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun addToDataSet(itemsToAdd: ArrayList<GithubUser>) {
        this.dataSet.addAll(itemsToAdd)
//        itemsToAdd.first().id?.let { notifyItemRangeInserted(it,itemsToAdd.count()) }
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.text = dataSet[position].login
        Glide.with(context).load(dataSet[position].avatar_url)
            .into(viewHolder.avatarImageView)
        viewHolder.itemView.tag = position
        viewHolder.itemView.setOnClickListener(this)

        Log.d("adapter", position.toString())

        if (position == dataSet.size - 1) {
            callback.getMoreUsers()
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
    override fun onClick(p0: View?) {
        callback.didSelectUser(dataSet[p0?.tag as Int])
    }


}