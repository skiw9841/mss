package com.example.mss.ui.github

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mss.api.RequestData
import com.example.mss.db.UserDao
import com.example.mss.model.User
import com.example.mss.util.ioThread

class LikesViewModel(private val dao: UserDao) : ViewModel() {

    private val queryLiveData = MutableLiveData<Int>()


    val usersLiveData: LiveData<ArrayList<User>> =
        Transformations.switchMap(queryLiveData) { i: Int ->
            Log.d("UsersViewModel", "Transformations.switchMap")
            dao.getUsersToLikes( ) as LiveData<ArrayList<User>>
        }

    fun getLikesUser(i:Int) {
        queryLiveData.postValue(i)
    }

    fun deleteLikes(id: Long){
        Log.d("deleteLikes", "id:$id")
        ioThread {
            dao.delete(id)
            getLikesUser(1)
        }
    }
}