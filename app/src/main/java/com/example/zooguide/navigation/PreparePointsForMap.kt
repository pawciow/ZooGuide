package com.example.zooguide.navigation

import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.io.File

class PreparePointsForMap{
    var points: MutableSet<NavigationPoint> = mutableSetOf()

    fun start(fileName: String){
        readLinesAndFetchPoints(fileName)
    }

    private fun readLinesAndFetchPoints(fileName: String){
        File(fileName).forEachLine{
            val parts = parseLine(it)
            val point = createPoints( parts.subList(0,2) )
            points.add(point)
        }
    }

    private fun parseLine(string: String) : List<String>{
        return string.split(";")
    }

    private fun parseConnections(string: String) : List<Int>{
        return string.split(",").map { it.toInt() }
    }


    private fun createPoints(parts: List<String>) : NavigationPoint{
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

    companion object fun distanceBetweenPoints(first: NavigationPoint, second: NavigationPoint) : Double{
        return SphericalUtil.computeDistanceBetween(first.coords, second.coords)
    }
}