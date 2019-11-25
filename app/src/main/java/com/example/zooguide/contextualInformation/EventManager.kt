package com.example.zooguide.contextualInformation

import android.content.Context
import android.content.res.AssetManager
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.zooguide.model.Event
import com.example.zooguide.navigation.PreparePointsForMap
import java.time.LocalTime
import java.util.*
import kotlin.coroutines.coroutineContext

class EventManager : AsyncTask<Void, Void, MutableList<Event>>() {
    private lateinit var _context: Context
    private lateinit var events : MutableList<Event>
    private val prepareEvents = PrepareEvents()

    private val INTERVAL : Long = 1000 * 60 * 15
    var mHandler = Handler()

    var mHandlerTask: Runnable = object : Runnable {
        override fun run() {

            val results = doInBackground()
            if (results.isNotEmpty()){
                var message = "Nadchodzące wydarzenia to: "
                for (result in results){
                    message += " \n ${result.description} o godzinie ${result.time}"
                }
                Toast.makeText(_context, message, Toast.LENGTH_LONG).show()
            }
            mHandler.postDelayed(this, INTERVAL)
        }
    }
    fun setupEvents(assetManager: AssetManager, fileName: String){
        events = prepareEvents.start(assetManager, fileName)
    }


    fun startRepeatingTask(context: Context) {
        _context = context
        mHandlerTask.run()
    }

    fun stopRepeatingTask() {
        mHandler.removeCallbacks(mHandlerTask)
    }

    override fun doInBackground(vararg params: Void?): MutableList<Event> {
        val toReturn = mutableListOf<Event>()
        val currentTime = LocalTime.now()
        for (event in events){
            if (currentTime in event.time.minusMinutes(15)..event.time){
                toReturn.add(event)
            }
        }
        return toReturn
    }

}


