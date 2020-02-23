package com.example.mss.api

import com.example.mss.model.User
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total_count")
    val total_count:Int = 0,
    @SerializedName("incomplete_results")
    val incomplete_results:Boolean = true,
    @SerializedName("items")
    val items: ArrayList<User> = ArrayList<User>()

)