package com.project.suitmediapalindrome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.suitmediapalindrome.model.User

class userAdapter(private val users: List<User>, private val onItemClick: (User) -> Unit) : RecyclerView.Adapter<userAdapter.UserViewHolder>(){

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)
        private val name: TextView = itemView.findViewById(R.id.rvName)
        private val email: TextView = itemView.findViewById(R.id.rvEmail)

        fun bind(user: User) {
            name.text = "${user.firstName} ${user.lastName}"
            email.text = user.email
            Glide.with(itemView.context)
                .load(user.avatar)
                .into(imageAvatar)

            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: userAdapter.UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

}