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
import com.example.mss.uiapp.now
import com.example.mss.uiapp.util.convertTimeToInt
import org.koin.dsl.koinApplication

class BarAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var reservationList = ArrayList<Boolean>()

    var mLinearLayoutManager: LinearLayoutManager? = null

    fun setLinearLayoutManager(linearLayoutManager: LinearLayoutManager?) {
        mLinearLayoutManager = linearLayoutManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BarViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_bar,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("BarAdapter", "position: $position")
        if (holder is BarViewHolder) {
            val reservationAvail = reservationList.get(position)
            if( reservationAvail ) {
                holder.binding.rect.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.blue
                    )
                )
            }
            else {
                holder.binding.rect.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.gray
                    )
                )
            }

            // 현재시간 눈금
            if( convertTimeToInt(now) == position )
                holder.binding.now.visibility = View.VISIBLE
            else
                holder.binding.now.visibility = View.INVISIBLE

            // 각 넓이 구하기
            val dm = holder.itemView.context.resources.displayMetrics
            holder.binding.frame.layoutParams.width = (dm.widthPixels / 20) // 18이 실제 사이즈고 2는 여백이여서 20으로 나눈다. 즉, 전체 화면 넓이의 90%만 사

            // 1800 예외처리, bar 오른쪽에 막대를 하나 만들어놓고 pos=17(17:30)일때 visible
            if( convertTimeToInt(now) == 18 && position == 17 ) holder.binding.six.visibility = View.VISIBLE
            else holder.binding.six.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    fun setRecyclerView(mView: RecyclerView) {
        mView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }



    internal inner class BarViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: ItemBarBinding

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }


    }

}


