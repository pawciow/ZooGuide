package com.example.zooguide.navigation

import android.content.res.AssetManager
import android.util.Log
import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.io.*

class PreparePointsForMap {
    public var points: MutableList<NavigationPoint> = mutableListOf()

    public fun start(assetManager: AssetManager, fileName: String): MutableList<NavigationPoint> {
        readLinesAndFetchPoints(assetManager, fileName)
        return points
    }

    private fun readLinesAndFetchPoints(assetManager: AssetManager, fileName: String) {
        var buffer: BufferedReader? = null
        try {
            buffer = assetManager.open(fileName).bufferedReader()
            buffer.use {
                it.forEachLine {
                    val parts = parseLine(it)
                    val point = createPoints(parts)
                    points.add(point)
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

    private fun parseConnections(string: String): List<Int> {
        return string.split(",").map { it.trim().toInt() }
    }

    fun createPoints(parts: List<String>): NavigationPoint {
        return NavigationPoint(
            parts[0].toInt(),
            LatLng(
                parts[1].toDouble(),
                parts[2].toDouble()
            ),
            parseConnections(
                parts[3]
            )
        )
    }
}