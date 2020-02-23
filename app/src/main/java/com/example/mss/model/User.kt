package com.example.mss.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "user")
data class User (
    @PrimaryKey @field:SerializedName( "id")
    val id:Long,
    @field:SerializedName("login")
    val login:String,
    @field:SerializedName("avatar_url")
    val image:String,
    @field:SerializedName("html_url")
    val url:String,

    var likes:Int
)
