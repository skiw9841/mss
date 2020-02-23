package com.example.mss.ui.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.mss.R
import com.example.mss.databinding.ActivityGithubBinding
import com.example.mss.ui.adapter.GithubViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy


class GithubActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var binding: ActivityGithubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github)
        binding.setLifecycleOwner (this)

        val fragmentList = arrayListOf<Fragment>(
            UsersFragment.newInstance(),
            LikesFragment.newInstance()
        )

        binding.vp.adapter = GithubViewPager2Adapter(
            this,
            fragmentList
        )

        TabLayoutMediator(binding.tl, binding.vp,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                when(position) {
                    0 -> tab.setText(R.string.tab1_users)
                    1 -> tab.setText(R.string.tab2_likes)
                }
            }
        ).attach()
    }
}