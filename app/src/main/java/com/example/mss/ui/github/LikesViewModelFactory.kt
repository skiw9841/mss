package com.example.mss.ui.github

import androidx.lifecycle.*
import com.example.mss.data.GithubRepository
import com.example.mss.db.UserDao

class LikesViewModelFactory(private val dao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LikesViewModel(dao) as T
    }
}