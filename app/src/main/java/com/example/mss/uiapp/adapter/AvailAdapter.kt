package com.example.mss.uiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mss.R
import com.example.mss.uiapp.model.Rooms
import com.example.mss.databinding.ItemAvailRoomsBinding

class AvailAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var roomsList = ArrayList<Rooms>()

    var mLinearLayoutManager: LinearLayoutManager? = null

    fun setLinearLayoutManager(linearLayoutManager: LinearLayoutManager?) {
        mLinearLayoutManager = linearLayoutManager
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AvailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_avail_rooms,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AvailViewHolder) {
            val rooms = roomsList.get(position)
            holder.binding.rooms = rooms
        }
    }

    override fun getItemCount(): Int {
        return roomsList.size
    }

    fun setRecyclerView(mView: RecyclerView) {
        mView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }



    internal inner class AvailViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: ItemAvailRoomsBinding

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }


    }

}


