package com.example.mss.uiapp.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mss.R
import com.example.mss.uiapp.model.Rooms
import com.example.mss.databinding.ItemAvailRoomsBinding
import com.example.mss.databinding.ItemBarBinding
import com.example.mss.databinding.ItemHourBinding
import org.koin.dsl.koinApplication

class HourAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var hourList = ArrayList<Int>()

    var mLinearLayoutManager: LinearLayoutManager? = null

    fun setLinearLayoutManager(linearLayoutManager: LinearLayoutManager?) {
        mLinearLayoutManager = linearLayoutManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HourViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hour,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("HourAdapter", "position: $position")
        if (holder is HourViewHolder) {
            val reservationAvail = hourList.get(position)
            val hour = hourList.get(position)
            holder.binding.hour.text = hour.toString()

            // 각 넓이 구하기
            val dm = holder.itemView.context.resources.displayMetrics
            holder.binding.hour.layoutParams.width = (dm.widthPixels / 10) // 전체 중 10등분으로 하면 된다.

        }
    }

    override fun getItemCount(): Int {
        return hourList.size
    }

    fun setRecyclerView(mView: RecyclerView) {
        mView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }



    internal inner class HourViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: ItemHourBinding

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }


    }

}


