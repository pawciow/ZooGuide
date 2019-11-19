package com.example.zooguide.navigation

import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class DistanceCalculator{
    fun distanceBetweenPoints(first: LatLng, second: LatLng) : Double {
        return SphericalUtil.computeDistanceBetween(first, second)
    }
}
