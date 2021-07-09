package com.example.redditclient

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.redditclient.Constants.ONE
import com.example.redditclient.Constants.ZERO
import com.example.redditclient.ui.AllPostsFragment
import com.example.redditclient.ui.FavouritePostsFragment

class ViewPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return Constants.NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            ZERO -> AllPostsFragment.getInstance(ZERO)
            ONE -> FavouritePostsFragment.getInstance(ONE)
            else -> AllPostsFragment.getInstance(ZERO)
        }
    }
}