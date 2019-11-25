package com.example.zooguide.contextualInformation

import android.content.res.AssetManager
import android.util.Log
import com.example.zooguide.model.Event
import java.io.*
import java.time.LocalTime
import java.util.*

class PrepareEvents {
    private var events: MutableList<Event> = mutableListOf()

    fun start(assetManager: AssetManager, fileName: String): MutableList<Event> {
        readLinesAndFetchPoints(assetManager, fileName)
        return events
    }

    private fun readLinesAndFetchPoints(assetManager: AssetManager, fileName: String) {
        var buffer: BufferedReader? = null
        try {
            buffer = assetManager.open(fileName).bufferedReader()
            buffer.use {
                it.forEachLine {
                    val parts = parseLine(it)
                    val point = createEvent(parts)
                    events.add(point)
                }
            }
        } catch (e: IOException) {
            Log.e("Preparation", " CANT READ LINES FROM ASSETS")
        } finally {
            if (buffer != null) {
                try {
                    buffer.close()
                } catch (e: IOException) {
                    Log.e("Preparation", " CANT CLOSE THE FILE BUFFER")
                }
            }
        }
    }

    private fun parseLine(string: String): List<String> {
        return string.split(";")
    }

    fun createEvent(parts: List<String>): Event {
        return Event(
            parts[0].toInt(),
            parts[1],
            LocalTime.parse(parts[2])
        )
    }
}