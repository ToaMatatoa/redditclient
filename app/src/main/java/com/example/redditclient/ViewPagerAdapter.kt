package com.example.redditclient

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.redditclient.ui.AllPostsFragment
import com.example.redditclient.ui.FavouritePostsFragment

class ViewPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllPostsFragment.getInstance(0)
            1 -> FavouritePostsFragment.getInstance(1)
            else -> AllPostsFragment.getInstance(0)
        }
    }
}