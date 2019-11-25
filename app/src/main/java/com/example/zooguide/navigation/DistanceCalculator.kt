package com.example.zooguide.navigation

import android.location.Location
import com.example.zooguide.model.Animal
import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class DistanceCalculator{
    private val distanceWhereItsClose = 100

    fun distanceBetweenPoints(first: LatLng, second: LatLng) : Double {
        return SphericalUtil.computeDistanceBetween(first, second)
    }

    private fun findClosestPoints(points: MutableList<Animal>, currentLocation: Location)
            : MutableList<Location> {
        val ediblePoints = mutableListOf<Location>()
        for (point in points){
            val pointLocation = createLocation(point.id.toString(), point.coords)
            if(currentLocation.distanceTo(pointLocation) < distanceWhereItsClose){
                ediblePoints.add(pointLocation)
            }
        }
        return ediblePoints
    }

    fun calculateBearingForClosestPoints(points: MutableList<Animal>, currentLocation: Location)
            : MutableList<Pair<Location, Float>>{
        val locationFromPoints = findClosestPoints(points, currentLocation)
        val pointsAndBearing : MutableList<Pair<Location, Float>> = mutableListOf()
        for (point in locationFromPoints){
            val bearing = currentLocation.bearingTo(point) + currentLocation.bearing
            pointsAndBearing.add(Pair(point, bearing))
        }

        return pointsAndBearing
    }

    fun createLocation(name: String, coordinates: LatLng) : Location{
        val location = Location(name)
        location.longitude = coordinates.longitude
        location.latitude = coordinates.latitude
        return location
    }
}
