package com.example.mss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.example.mss.databinding.ActivityMainBinding
import com.example.mss.ui.github.GithubActivity
import com.example.mss.uiapp.UiActivity

class MainActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setActivity(this)

    }

    fun onGithubClick(view: View) {
        val intent = Intent(this, GithubActivity::class.java)
        startActivity(intent)
    }

    fun onUiClick(view: View) {
        val intent = Intent(this, UiActivity::class.java)
        startActivity(intent)
    }


}