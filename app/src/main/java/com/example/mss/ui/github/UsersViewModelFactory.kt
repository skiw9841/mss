package com.example.mss.ui.github

import androidx.lifecycle.*
import com.example.mss.data.GithubRepository
import com.example.mss.db.UserDao

class UsersViewModelFactory( private val repository: GithubRepository, private val dao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(repository, dao) as T
    }
}