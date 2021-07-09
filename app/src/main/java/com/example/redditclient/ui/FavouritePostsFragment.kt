package com.example.redditclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.redditclient.Constants.ARG_POSITION
import com.example.redditclient.R
import com.example.redditclient.databinding.FragmentFavouriteBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

class FavouritePostsFragment : Fragment(R.layout.fragment_favourite), KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)
    private val parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
    }
    override val kodeinTrigger = KodeinTrigger()

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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