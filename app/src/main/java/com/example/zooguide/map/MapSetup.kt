package com.example.zooguide.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.zooguide.R
import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class MapSetup{

    fun setupCamera(googleMap: GoogleMap, Zoo : LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(Zoo)
            .zoom(20F)
            .bearing(180F)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun placeImageOnMap(googleMap: GoogleMap, Zoo: LatLng, ZooMap: Bitmap) {
        val zooGroundOverlayOptions = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(ZooMap))
            .position(Zoo,1100F, 1100F)
        googleMap.addGroundOverlay(zooGroundOverlayOptions)
    }

   fun placeImageOnMap(googleMap: GoogleMap, ZooBorders: LatLngBounds, ZooMap : Bitmap) {
        val zooGroundOverlayOptions = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(ZooMap))
            .positionFromBounds(ZooBorders)
        googleMap.addGroundOverlay(zooGroundOverlayOptions)
    }
    private fun cutBitmapInPieces(bigPicture : Bitmap) : MutableList<Bitmap>{
        var toReturn = mutableListOf<Bitmap>()
        val width = 1024
        val height = 1024
        val it = 1
        for (x in 0..it)
            for(y in 0..it)
                toReturn.add(Bitmap.createBitmap(bigPicture, x * width, y * height, width, height))
        return toReturn
    }

    fun getPolyLine(points: MutableList<NavigationPoint>) : PolylineOptions{
        val toReturn = PolylineOptions()
        for (point in points) {
            toReturn.add(point.coords)
        }

        return toReturn
    }
}