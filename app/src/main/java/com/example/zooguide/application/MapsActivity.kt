package com.example.zooguide.application

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.scale
import com.example.zooguide.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
//    private lateinit var zooGroundOverlayOptions: GroundOverlayOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Log.e("starting_state", " MAPS IS CREATED< IT SHOULD WORK")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val Zoo = LatLng(51.104210, 17.07446)

        val ZooBordersNE = LatLng(51.102264, 17.074860 )
        val ZooBordersSW = LatLng(51.106242,17.077893)
        val ZooBorders = LatLngBounds(ZooBordersNE,ZooBordersSW)
        mMap.addMarker(MarkerOptions().position(Zoo).title("Marker Zoo"))
        setupCamera(mMap,Zoo)
//        cutBitmapInPieces()
    }

    private fun calculatePartsBorders(zooNEBorders: LatLng, zooSWBorderd: LatLng){

    }

    private fun setupCamera(googleMap: GoogleMap, Zoo : LatLng){
        mMap = googleMap
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(Zoo, 15F)
        )
    }

    private fun placeImageOnMap(ZooBorders: LatLngBounds, ZooMap : Bitmap) {
        val zooGroundOverlayOptions = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(ZooMap))
            .positionFromBounds(ZooBorders)
        mMap.addGroundOverlay(zooGroundOverlayOptions)
    }

    private fun cutBitmapInPieces(bigPicture : Bitmap) : MutableList<Bitmap>{
        var toReturn = mutableListOf<Bitmap>()
        val width = 2048
        val height = 2048
        val it = 2
        for (x in 0..it)
            for(y in 0..it)
                toReturn.add(Bitmap.createBitmap(bigPicture, x * width, y * height, width, height))
        return toReturn
    }
}
