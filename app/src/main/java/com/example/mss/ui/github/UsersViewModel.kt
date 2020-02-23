package com.example.mss.ui.github

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mss.api.RequestData
import com.example.mss.data.GithubRepository
import com.example.mss.db.UserDao
import com.example.mss.model.User
import com.example.mss.model.UserSearchResult
import com.example.mss.util.ioThread
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel(private val repository: GithubRepository, private val dao: UserDao) : ViewModel() {

    private val searchLiveData = MutableLiveData<RequestData>()
    private val getLikesLiveData = MutableLiveData<Int>()

    val usersLiveData: LiveData<ArrayList<User>> =
            Transformations.switchMap(searchLiveData) { data: RequestData ->
            Log.d("UsersViewModel", "usersLiveData")
            repository.search( data )
        }

    val likesLiveData: LiveData<ArrayList<Long>> =
        Transformations.switchMap(getLikesLiveData) {
            Log.d("UsersViewModel", "likesLiveData")
            //dao.isUsers( list )
            dao.getIdsToLikes() as LiveData<ArrayList<Long>>
        }

    fun searchUser(data: RequestData) {
        searchLiveData.postValue(data)
    }

    fun getAllLikes() {
        getLikesLiveData.postValue(1)
    }

    fun saveLikes(user: User){
        Log.d("saveLikes", "id:${user.id}")
        ioThread {
            dao.insert(user)
            getAllLikes()
        }
    }

    fun deleteLikes(id: Long){
        Log.d("deleteLikes", "id:$id")
        ioThread {
            dao.delete(id)
            getAllLikes()
        }
    }

}