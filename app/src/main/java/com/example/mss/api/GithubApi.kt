package com.example.mss.api

import com.example.mss.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
//q=abc%20in:login&page=2&per_page=5
    @GET("/search/users")
    fun getSearchUsers(@Query("q") q:String,
                         @Query("page") pageCnt:Int,
                         @Query("per_page") perPage:Int ): Call<SearchResponse>
}