package com.example.mss.ui.github

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mss.R
import com.example.mss.api.RequestData
import com.example.mss.data.GithubRepository
import com.example.mss.databinding.FragmentUsersBinding
import com.example.mss.db.UserDao
import com.example.mss.model.User
import com.example.mss.model.UserSearchResult
import com.example.mss.ui.adapter.UsersAdapter
import com.example.mss.util.QUERY_IN_LOGIN
import com.example.mss.util.hideKeyboard
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


class UsersFragment : Fragment(), UsersAdapter.OnLoadMoreListener, AdapterView.OnItemClickListener{

    lateinit var binding: FragmentUsersBinding

    private val repository: GithubRepository by inject()
    private val daoUser: UserDao by inject()
    /* Connect factory to injected repository */
    private var usersViewModelFactory: UsersViewModelFactory= UsersViewModelFactory(repository, daoUser)

    private lateinit var viewModel: UsersViewModel

     val adapter = UsersAdapter(this, this)

    private var requestData: RequestData = RequestData("", 1, 20)

    private val idsLikesHashSet = HashSet<Long>()

    companion object{
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            getViewModelStore(),
            usersViewModelFactory!!
        )[UsersViewModel::class.java]
        binding.setLifecycleOwner(viewLifecycleOwner)

        //binding.vm = getViewModel() // koin
        binding.btSearch.setOnClickListener {
            // clear list
            adapter.clearAll()
            requestData.query = binding.etSearch.text.toString().trim() + QUERY_IN_LOGIN
            requestData.page = 1
            searchUser()

            hideKeyboard(context, view)
        }

        binding.btClear.setOnClickListener{
            adapter.clearAll()
            binding.etSearch.text.clear()
        }

        // adapter set & observer
        initAdapter()

        // get likes data in DB
        viewModel.getAllLikes()
    }

    private fun initAdapter() {
        /* set adapter of recyclerView */
        val layoutManager = LinearLayoutManager(context)
        binding.rvUsers.layoutManager = layoutManager
        adapter.setLinearLayoutManager(layoutManager)
        adapter.setRecyclerView(binding.rvUsers)
        binding.rvUsers.adapter = adapter

        /* observe user list */
        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer { list:ArrayList<User> ->
            Log.d("UsersFragment", "list: ${list.size}")
            // add list
            adapter.addMore(list)
            // set likes
            adapter.setLikes(idsLikesHashSet)
            requestData.page ++ // load next page
        })

        /* observe likes list */
        viewModel.likesLiveData.observe(viewLifecycleOwner, Observer { idList: ArrayList<Long> ->
            // update ids of likes
            idsLikesHashSet.clear()
            idsLikesHashSet.addAll(idList)

            // set likes
            adapter.setLikes(idsLikesHashSet)
        })
    }

    override fun onLoadMore() {
        Log.d("onLoadMore", "requestData:"+requestData)
        Handler().postDelayed({
            searchUser()
            adapter.moreLoading = false
        },10)
    }

    fun searchUser() {
        Log.d("UsersFragment", "SearchClick")
        viewModel.searchUser( requestData )
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
//        Toast.makeText(context, "pos=$i", Toast.LENGTH_SHORT).show()
        Log.d("onClick", "position:" + i)
        if( adapter.isLikes(i) == 1 ) { // if view is likes then unlikes
            viewModel.deleteLikes(adapter.userList.get(i).id)
        }
        else {
            viewModel.saveLikes(adapter.userList.get(i))
        }
    }




}


