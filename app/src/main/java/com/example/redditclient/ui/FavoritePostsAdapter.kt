package com.example.redditclient.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.redditclient.R
import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.databinding.RvFavoriteItemBinding

class FavoritePostsAdapter(
    private var listener: OnItemClickListener,
    private var favoriteListener: OnFavoriteClickListener
) :
    RecyclerView.Adapter<FavoritePostsAdapter.ViewHolder>() {

    private val items = mutableListOf<FavoritePost>()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(post: FavoritePost)
    }

    fun addFavoritePosts(favoritePosts: List<FavoritePost>) {
        items.clear()
        items.addAll(favoritePosts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            RvFavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postPosition: FavoritePost = items[position]
        holder.bind(postPosition, listener, favoriteListener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var itemBinding: RvFavoriteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            item: FavoritePost, listener: OnItemClickListener?,
            favoriteListener: OnFavoriteClickListener?
        ) =
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
                itemBinding.postInfo.text = item.info

                if (listener != null) {
                    itemBinding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
                }

                itemBinding.ivFavorite.isChecked = false

                if (favoriteListener != null) {
                    itemBinding.ivFavorite.setOnCheckedChangeListener { _, _ ->
                        favoriteListener.onFavoriteClick(
                            item
                        )
                    }
                }
            }
    }
}