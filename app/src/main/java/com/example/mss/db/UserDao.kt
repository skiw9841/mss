package com.example.mss.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.mss.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user WHERE id=:id")
    fun delete(id: Long)

    @Query("SELECT * FROM user ORDER BY login")
    fun getUsersToLikes(): LiveData<List<User>>

    @Query( "SELECT id FROM user" )
    fun getIdsToLikes(): LiveData<List<Long>>

}
