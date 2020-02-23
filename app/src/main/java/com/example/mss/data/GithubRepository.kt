package com.example.mss.data

import androidx.lifecycle.MutableLiveData
import com.example.mss.api.RequestData
import com.example.mss.model.User
import com.example.mss.model.UserSearchResult

interface GithubRepository{
    fun search(data: RequestData): MutableLiveData<ArrayList<User>>

}