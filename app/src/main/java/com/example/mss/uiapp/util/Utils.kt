package com.example.mss.uiapp.util

fun convertTimeToInt(time: String): Int{
    val value = time.toInt()
    return ( value / 100 - 9 ) * 2 + ( value % 100 ) / 30
}


fun convertTimeToInt(value: Int): Int{
    return ( value / 100 - 9 ) * 2 + ( value % 100 ) / 30
}
