package com.example.mss.ui.github

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mss.R
import com.example.mss.databinding.FragmentLikesBinding
import com.example.mss.databinding.FragmentUsersBinding
import com.example.mss.db.UserDao
import com.example.mss.model.User
import com.example.mss.ui.adapter.LikesAdapter
import com.example.mss.ui.adapter.UsersAdapter
import com.example.mss.util.ioThread
import org.koin.android.ext.android.inject


class LikesFragment : Fragment(), AdapterView.OnItemClickListener {

    lateinit var binding: FragmentLikesBinding
    private val daoUser: UserDao by inject()
    /* Connect factory to injected repository */
    private var likesViewModelFactory: LikesViewModelFactory = LikesViewModelFactory(daoUser)

    private lateinit var viewModel: LikesViewModel

    private val adapter = LikesAdapter(this)

    companion object {
        fun newInstance() = LikesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_likes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            getViewModelStore(),
            likesViewModelFactory!!
        )[LikesViewModel::class.java]
        binding.setLifecycleOwner(viewLifecycleOwner)

        initAdapter()
    }

    private fun initAdapter() {
        /* set adapter of recyclerView */
        val layoutManager = LinearLayoutManager(context)
        binding.rvLikes.layoutManager = layoutManager
        adapter.setLinearLayoutManager(layoutManager)
        adapter.setRecyclerView(binding.rvLikes)
        binding.rvLikes.adapter = adapter

        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer { list:ArrayList<User> ->
            Log.d("UsersFragment", "list: ${list.size}")
            adapter.clearAll()
            adapter.addMore(list)
        })

        viewModel.getLikesUser(1)
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        Log.d("onClick", "position:" + i)
        viewModel.deleteLikes(adapter.userList.get(i).id)
    }

}