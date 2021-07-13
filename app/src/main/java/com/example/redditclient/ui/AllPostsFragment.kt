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
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import com.example.redditclient.databinding.FragmentAllBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class AllPostsFragment : Fragment(R.layout.fragment_all), AllPostsAdapter.OnItemClickListener,
    AllPostsAdapter.OnFavoriteClickListener, KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)
    private val parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
    }
    override val kodeinTrigger = KodeinTrigger()

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by instance()
    private var allPostsAdapter: AllPostsAdapter? = null

    var pageNumber = 1

    override fun onItemClick(position: Int) {
        context?.let { viewModel.openEntryInChromeTab(position, it) }
    }

    override fun onFavoriteClick(post: Data) {
        if (!post.isFavorite) viewModel.deletePost(post.id)
        else viewModel.savePost(post)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        kodeinTrigger.trigger()

        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allPostsAdapter = AllPostsAdapter(this, this)

        viewModel.liveDataRemoteProvider.observe(viewLifecycleOwner, { posts ->
            allPostsAdapter?.addPosts(posts)
            stopAnimation()
        })

        viewModel.loadAllPosts()

        binding.rvAllPostsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allPostsAdapter
        }

        binding.srlAllPosts.setColorSchemeResources(
            R.color.middle_gray
        )

        binding.srlAllPosts.setOnRefreshListener {
            refreshAllPosts()
        }

        binding.rbPrev.setOnClickListener {
            viewModel.loadPrevPostsPage()
            pageNumber -= 1
            binding.rbPrev.isVisible = pageNumber >= 2
        }

        binding.rbNext.setOnClickListener {
            viewModel.loadNextPostsPage()
            pageNumber += 1
            binding.rbPrev.isVisible = pageNumber >= 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshAllPosts() {
        viewModel.loadAllPosts()
        Handler().postDelayed(Runnable {
            binding.srlAllPosts.isRefreshing = false
        }, 2000)
    }

    private fun stopAnimation() {
        binding.progressBarAllPosts.apply {
            isVisible = false
            cancelAnimation()
        }
    }

    companion object {
        fun getInstance(position: Int): Fragment {
            val allPostsFragment = AllPostsFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            allPostsFragment.arguments = bundle
            return allPostsFragment
        }
    }
}