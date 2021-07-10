package com.example.redditclient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.redditclient.R
import com.example.redditclient.data.local.model.LocalData
import com.example.redditclient.databinding.RvAllItemBinding

class FavoritePostsAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<FavoritePostsAdapter.ViewHolder>() {

    private val items = mutableListOf<LocalData>()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun addFavoritePosts(favoritePosts: List<LocalData>) {
        items.clear()
        items.addAll(favoritePosts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            RvAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postPosition: LocalData = items[position]
        holder.bind(postPosition, listener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var itemBinding: RvAllItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: LocalData, listener: OnItemClickListener?) =
            with(itemView) {
                Glide.with(this)
                    .load(item.thumbnail)
                    .placeholder(R.drawable.img_no_img_available)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
                    .dontAnimate()
                    .into(itemBinding.ivPostImage)

                itemBinding.tvPostTitle.text = item.title
                itemBinding.tvPostRating.text = item.numLikes.toString()
                itemBinding.tvCountOfPostComments.text = item.numComments.toString()
                itemBinding.numberOfPost.text = item.numOfEntry.toString()
                itemBinding.postInfo.text = item.info

                if (listener != null) {
                    itemBinding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
                }
            }
    }
}