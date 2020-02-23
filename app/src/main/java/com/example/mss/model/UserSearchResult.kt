package com.example.mss.model

import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserSearchResult (
    val items: ArrayList<User>
)