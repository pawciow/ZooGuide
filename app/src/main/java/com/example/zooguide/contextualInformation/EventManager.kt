package com.example.zooguide.contextualInformation

import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import com.example.zooguide.model.Event
import com.example.zooguide.navigation.PreparePointsForMap
import java.util.*

class EventManager : AsyncTask<Event, Void, Boolean>() {

    private val INTERVAL : Long = 1000 * 60 * 15
    var mHandler = Handler()

    var mHandlerTask: Runnable = object : Runnable {
        override fun run() {
            doInBackground()
            mHandler.postDelayed(this, INTERVAL)
        }
    }

    fun startRepeatingTask() {
        mHandlerTask.run()
    }

    fun stopRepeatingTask() {
        mHandler.removeCallbacks(mHandlerTask)
    }

    override fun doInBackground(vararg params: Event): Boolean {
        for (param in params){
            param.time

        }

        val start = 21
        val end = 7
        val hours = 24 - start + end

        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, start)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val startHourMilli = cal.timeInMillis
        Log.e("EventManager", cal.time.toString())

        cal.add(Calendar.HOUR_OF_DAY, hours)
        val endHourMilli = cal.timeInMillis

        val currentMilli = Calendar.getInstance().timeInMillis


        return currentMilli in startHourMilli..endHourMilli
    }
}
private class PrepareEvents : PreparePointsForMap() {


}

