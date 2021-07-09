package com.example.redditclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redditclient.R
import com.example.redditclient.databinding.FragmentAllBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class AllPostsFragment : Fragment(R.layout.fragment_all), KodeinAware {

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

        allPostsAdapter = AllPostsAdapter()

        viewModel.liveDataRemoteProvider.observe(viewLifecycleOwner, Observer { posts ->
            allPostsAdapter?.addPosts(posts)
        })

        viewModel.loadTopEntries()

        binding.rvAllPostsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allPostsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val allPostsFragment = AllPostsFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            allPostsFragment.arguments = bundle
            return allPostsFragment
        }
    }
}