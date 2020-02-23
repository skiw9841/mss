package com.example.mss.uiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mss.R
import com.example.mss.databinding.ItemAvailRoomsBinding
import com.example.mss.databinding.ItemRoomsBinding
import com.example.mss.uiapp.model.Rooms
import com.example.mss.uiapp.now
import com.example.mss.uiapp.util.convertTimeToInt

class RoomsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var roomsList = ArrayList<Rooms>()
    var mLinearLayoutManager: LinearLayoutManager? = null

    fun setLinearLayoutManager(linearLayoutManager: LinearLayoutManager?) {
        mLinearLayoutManager = linearLayoutManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RoomsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_rooms,
                parent,
                false
            )
        )
    }

    val barAdapterList = ArrayList<BarAdapter>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RoomsViewHolder) {
            val rooms = roomsList.get(position)
            holder.binding.rooms = rooms

            /* bar */
            val barAdapter = BarAdapter()
            val barLayoutManager = LinearLayoutManager(holder.itemView.context)
            barLayoutManager.orientation = LinearLayoutManager.HORIZONTAL // 가로
            holder.binding.rvBar.layoutManager = barLayoutManager
            barAdapter.setLinearLayoutManager(barLayoutManager)
            barAdapter.setRecyclerView(holder.binding.rvBar)
            holder.binding.rvBar.adapter = barAdapter

            barAdapter.reservationList = getReservationList(rooms)
            // 넓이 계산을 위하여 bar의 넓이를 adapter에 넘겨준다
            val dm = holder.itemView.context.resources.displayMetrics
            holder.binding.rvBar.layoutParams.width = dm.widthPixels / 20 * 18 // bar의 넓이를 전체 화면의 90%만 잡는다.

            /* hour 표시 */
            val hourAdapter = HourAdapter()
            val hourLayoutManager = LinearLayoutManager(holder.itemView.context)
            hourLayoutManager.orientation = LinearLayoutManager.HORIZONTAL // 가로
            holder.binding.rvHour.layoutManager = hourLayoutManager
            hourAdapter.setLinearLayoutManager(hourLayoutManager)
            hourAdapter.setRecyclerView(holder.binding.rvHour)
            holder.binding.rvHour.adapter = hourAdapter

            hourAdapter.hourList = arrayListOf(9, 10, 11, 12, 13, 14, 15, 16, 17, 18)
            //val dm = holder.itemView.context.resources.displayMetrics
            holder.binding.rvHour.layoutParams.width = dm.widthPixels  // bar의 넓이를 전체 화면의 100%를 잡는다.

            /* 현재시간 위치 */
            if( convertTimeToInt(now) < 0 || convertTimeToInt( now) > 18 ) holder.binding.tvCurrent.visibility = View.INVISIBLE
            else {
                val width =
                    holder.binding.tvCurrent.paint.measureText(holder.itemView.context.getString(R.string.current_time)) // 현재시간의 넓이를 구해옴
                var paddingLeft =
                    ((convertTimeToInt(now) + 0.5) * dm.widthPixels / 20 - (width / 2)).toInt()
                if (paddingLeft < 0) paddingLeft = 0
                else if (paddingLeft > dm.widthPixels * 18.5 / 20 - width) paddingLeft =
                    (dm.widthPixels * 18.5 / 20 - width).toInt()
                holder.binding.tvCurrent.setPadding(paddingLeft, 0, 0, 0)
            }
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

    fun getReservationList(rooms: Rooms): ArrayList<Boolean> {
        var avail= Array(18, {true})

        for( reservations in rooms.reservations ) {
            val start = convertTimeToInt( reservations.startTime )
            val end = convertTimeToInt( reservations.endTime )

            for( i in start..end-1 ) {
                avail[i] = false
            }
        }

        return avail.toCollection(ArrayList())
    }

    internal inner class RoomsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var binding: ItemRoomsBinding
        init {
            binding = DataBindingUtil.bind(itemView)!!

        }
    }

}


