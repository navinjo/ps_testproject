package com.ndbg.ps_cp.util

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar

object TodayDate {

    fun getTodayDate():String{
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        return formatter.format(date)
    }
}