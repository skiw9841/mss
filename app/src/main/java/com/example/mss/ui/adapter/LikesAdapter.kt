package com.example.mss.ui.adapter

import android.R.attr.data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mss.R
import com.example.mss.databinding.ItemUserBinding
import com.example.mss.model.User


class LikesAdapter(private val onItemClickListener: AdapterView.OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val userList: ArrayList<User> = ArrayList()
    var mLinearLayoutManager: LinearLayoutManager? = null

    fun setLinearLayoutManager(linearLayoutManager: LinearLayoutManager?) {
        mLinearLayoutManager = linearLayoutManager
    }

    fun setRecyclerView(mView: RecyclerView) {
        mView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return     UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val singleItem = userList.get(position)
            holder.binding!!.user = singleItem
        }
    }

    /* add users to userList */
    fun addMore(addList: ArrayList<User>) {
        userList.addAll(addList)
        notifyItemRangeChanged(userList.size-addList.size, addList.size)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun clearAll() {
        val size = userList.size
        userList.clear()
        notifyItemRangeRemoved(0, size)
    }

    internal inner class UserViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var binding: ItemUserBinding

        init {
            binding = DataBindingUtil.bind(itemView)!!
            itemView.setOnClickListener(this)
            binding.btLikes.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onItemClickListener.onItemClick(null, view, adapterPosition, view.id.toLong())
        }
    }

}