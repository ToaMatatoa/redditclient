package com.example.redditclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redditclient.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

class MainActivity : AppCompatActivity(), KodeinAware {

    private val parentKodein: Kodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
    }
    override val kodeinTrigger = KodeinTrigger()

    private lateinit var binding: ActivityMainBinding

    private val tabItems = listOf("All", "Favorite")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kodeinTrigger.trigger()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabItems[position].substringBefore(" ")
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewPagerAdapter.createFragment(0)
                    1 -> viewPagerAdapter.createFragment(1)
                    else -> viewPagerAdapter.createFragment(0)
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                //ToDo
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                //ToDO
            }
        })
    }
}