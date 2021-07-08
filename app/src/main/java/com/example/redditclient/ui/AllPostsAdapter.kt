package com.example.redditclient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.redditclient.R
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.databinding.RvAllItemBinding

class AllPostsAdapter : RecyclerView.Adapter<AllPostsAdapter.ViewHolder>() {

    private val items = mutableListOf<Children>()

    fun addPosts(booksItems: List<Children>) {
        items.clear()
        items.addAll(booksItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            RvAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postPosition: Children = items[position]
        holder.bind(postPosition)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var itemBinding: RvAllItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Children) =
            with(itemView) {
                Glide.with(this)
                    .load(item.data.thumbnail)
                    .placeholder(R.drawable.img_placeholder)
                    .dontAnimate()
                    .into(itemBinding.ivPostImage)

                itemBinding.tvPostTitle.text = item.data.title ?: ""
            }
    }
}