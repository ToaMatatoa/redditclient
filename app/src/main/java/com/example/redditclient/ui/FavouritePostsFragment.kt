package com.example.redditclient.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditclient.Constants.ARG_POSITION
import com.example.redditclient.R
import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.databinding.FragmentFavouriteBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class FavouritePostsFragment : Fragment(R.layout.fragment_favourite),
    FavoritePostsAdapter.OnItemClickListener, FavoritePostsAdapter.OnFavoriteClickListener, KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)
    private val parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
    }
    override val kodeinTrigger = KodeinTrigger()

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by instance()
    private var favoritePostsAdapter: FavoritePostsAdapter? = null

    override fun onItemClick(position: Int) {
        context?.let { viewModel.openEntryInChromeTabFromFavorite(position, it) }
    }

    override fun onFavoriteClick(post: FavoritePost) {
        viewModel.deletePost(post.id)
        viewModel.loadFavoritePosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        kodeinTrigger.trigger()

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritePostsAdapter = FavoritePostsAdapter(this, this)

        viewModel.liveDataLocalProvider.observe(viewLifecycleOwner, { localPosts ->
            favoritePostsAdapter?.addFavoritePosts(localPosts)
            stopAnimation()
        })

        viewModel.loadFavoritePosts()

        binding.rvFavoritePostsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoritePostsAdapter
        }

        binding.srlFavoritePosts.setOnRefreshListener {
            refreshAllPosts()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavoritePosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun stopAnimation() {
        binding.progressBarFavoritePosts.apply {
            isVisible = false
            cancelAnimation()
        }
    }

    private fun refreshAllPosts() {
        viewModel.loadFavoritePosts()
        Handler().postDelayed(Runnable {
            binding.srlFavoritePosts.isRefreshing = false
        }, 2000)
    }

    companion object {
        fun getInstance(position: Int): Fragment {
            val favouritePostsFragment = FavouritePostsFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            favouritePostsFragment.arguments = bundle
            return favouritePostsFragment
        }
    }
}