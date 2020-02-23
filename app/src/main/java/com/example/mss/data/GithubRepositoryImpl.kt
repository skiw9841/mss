package com.example.mss.data

import androidx.lifecycle.MutableLiveData
import com.example.mss.api.GithubApi
import com.example.mss.api.RequestData
import com.example.mss.api.SearchResponse
import com.example.mss.model.User
import com.example.mss.model.UserSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepositoryImpl(private val api: GithubApi/*, private val db: GithubDB*/ ): GithubRepository{
    private var isLoading = false

    override fun search(data: RequestData): MutableLiveData<ArrayList<User>>{
        val usersData = MutableLiveData<ArrayList<User>>()
        api.getSearchUsers( data.query, data.page, data.perPage )
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    if (response.isSuccessful) {
                        if( response.body() != null ) {
                            usersData.value = response.body()!!.items
                        }
                    }
                }
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                }
            })
        return usersData
    }


}