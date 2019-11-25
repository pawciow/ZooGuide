package com.example.zooguide.contextualInformation

import android.location.Location
import com.example.zooguide.model.Animal
import com.example.zooguide.model.NavigationPoint
import com.example.zooguide.navigation.DistanceCalculator
import com.google.android.gms.maps.model.LatLng

class CompassManager(location: Location) {
    private val _location = location
    private lateinit var distanceCalculator: DistanceCalculator
    fun getAllPoints(points: MutableList<Animal>): MutableList<Pair<Animal?, Direction?>> {
        distanceCalculator = DistanceCalculator()
        val directions = mutableListOf<Pair<Animal?, Direction?>>()

        val tmp = distanceCalculator.calculateBearingForClosestPoints(points, _location)
        for (point in tmp) {
            val direction = getDirection(point.second)
            val animal = getAnimalFromLocation(point.first, points)
            directions.add(Pair(animal, direction))
        }
        return directions
    }

    private fun getAnimalFromLocation(location: Location, points: MutableList<Animal>) : Animal?{
        val latLng = LatLng(location.latitude, location.longitude)
        for (animal in points){
            if (animal.coords == latLng)
                return animal
        }
        return null
    }

    private fun getDirection(bearing: Float) : Direction? {
        if (bearing < 15 || bearing > 345)
            return Direction.Ahead
        else if (bearing > 15 && bearing < 90)
            return Direction.Right
        else if (bearing > 90 && bearing < 165)
            return Direction.BackwardRight
        else if (bearing> 165 && bearing < 195)
            return Direction.Backward
        else if (bearing > 195 && bearing < 270)
            return Direction.BackwardLeft
        else if (bearing > 270 && bearing < 345)
            return Direction.Left

        return null
    }
}

enum class Direction{
    Left, Ahead, Right, BackwardRight, Backward, BackwardLeft
}

