package com.example.zooguide.navigation

import android.location.Location
import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class DistanceCalculator{
    private val distanceWhereItsClose = 100.0

    fun distanceBetweenPoints(first: LatLng, second: LatLng) : Double {
        return SphericalUtil.computeDistanceBetween(first, second)
    }

    private fun findClosestPoints(points: MutableList<NavigationPoint>, currentLocation: Location)
            : MutableList<Location> {
        var ediblePoints = mutableListOf<Location>()
        for (point in points){
            val pointLocation = createLocation(point.id.toString(), point.coords)
            if(currentLocation.distanceTo(pointLocation) < distanceWhereItsClose){
                ediblePoints.add(pointLocation)
            }
        }
        return ediblePoints
    }

    fun calculateBearingForClosestPoints(points: MutableList<NavigationPoint>, currentLocation: Location)
            : MutableList<Pair<Location, Float>>{
        val locationFromPoints = findClosestPoints(points, currentLocation)
        var pointsAndBearing : MutableList<Pair<Location, Float>> = mutableListOf()
        for (point in locationFromPoints){
            val bearing = currentLocation.bearingTo(point)
            pointsAndBearing.add(Pair(point, bearing))
        }

        return pointsAndBearing
    }

    fun createLocation(name: String, coordinates: LatLng) : Location{
        var location = Location(name)
        location.longitude = coordinates.longitude
        location.latitude = coordinates.latitude
        return location
    }
}
