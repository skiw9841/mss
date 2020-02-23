package com.example.mss.uiapp

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mss.R
import com.example.mss.uiapp.adapter.AvailAdapter
import com.example.mss.uiapp.model.Rooms
import com.example.mss.databinding.ActivityUiBinding
import com.example.mss.uiapp.adapter.RoomsAdapter
import com.example.mss.uiapp.util.convertTimeToInt
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


/* 현재시간 */
var now = 1305

class UiActivity  : AppCompatActivity(), LifecycleOwner {

    lateinit var binding: ActivityUiBinding
    val availAdapter = AvailAdapter()
    val roomsAdapter = RoomsAdapter()
    lateinit var roomList: ArrayList<Rooms>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui)
        binding.setActivity(this)

        /* json 값을 ArrayList에 셋팅한다 */
        roomList = readJsonFile()

        /* 현재시간 버림 재설정 */
        now = roundsTime(now)
        /* 이용가능한 회의실 목록 가져오기 */
        val nowAvailList = getNowAvailList(now, roomList)
        /* 현재 사용 가능 회의실을 위한 adapter 설정 */
        initAvailAdapter()
        /* 현재 이용가능 회의실 adapter에 추가 */
        addNowAvailList(nowAvailList)
        binding.tvRoomCount.setText(availAdapter.roomsList.size.toString())

        /* 회의실 recyclerView */
        initRoomsAdapter()
        roomsAdapter.roomsList.addAll(roomList)


    }

    /* read json file */
    fun readFileString(resId: Int) :String {
        var inputStream = resources.openRawResource(resId)

        val outputStream = ByteArrayOutputStream()

        val buf = ByteArray(1024)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) { }
        return outputStream.toString()

    }

    fun readJsonFile(): ArrayList<Rooms> {
        val jsonString = readFileString(R.raw.data)
        val gson = Gson()
        val rooms: Array<Rooms> = gson.fromJson(jsonString, Array<Rooms>::class.java)
        return rooms.toCollection(ArrayList())
    }

    /* avail adapter */
    fun initAvailAdapter(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL // 가로
        binding.rvAvailRooms.layoutManager = layoutManager
        availAdapter.setLinearLayoutManager(layoutManager)
        availAdapter.setRecyclerView(binding.rvAvailRooms)
        binding.rvAvailRooms.adapter = availAdapter
    }

    /* rooms adapter */
    fun initRoomsAdapter(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvRooms.layoutManager = layoutManager
        roomsAdapter.setLinearLayoutManager(layoutManager)
        roomsAdapter.setRecyclerView(binding.rvRooms)
        binding.rvRooms.adapter = roomsAdapter
    }

    fun roundsTime(now:Int): Int{
        val hour:Int = now/100
        var min:Int = now%100

        if( min <= 29 ) min = 0
        else if( 30 < min && min <= 59 ) min = 30

        return hour * 100 + min
    }

    fun getNowAvailList(now: Int, roomList: ArrayList<Rooms>): ArrayList<Boolean>{
        var nowAvailList = ArrayList<Boolean>()
        for( room in roomList ) {
            var check = true
            for( reservation in room.reservations ) {
                // not use
                if( reservation.startTime.toInt() <= now && now < reservation.endTime.toInt() ) {
                    check = false
                    break;
                }
            }
            nowAvailList.add(check)
        }
        return nowAvailList
    }

    fun addNowAvailList(nowAvailList: ArrayList<Boolean>) {
        for( i in 0..nowAvailList.size-1 ) {
            if( nowAvailList.get(i) )
                availAdapter.roomsList.add(roomList.get(i))
        }
    }
}
