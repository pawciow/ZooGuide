package com.example.zooguide.application

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.zooguide.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.CameraPosition




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
        val Zoo = LatLng(51.104210, 17.07446)


        val marker1 = LatLng(51.102024, 17.076698)
        val marker2 = LatLng(51.103318, 17.075869)


        mMap.addMarker(MarkerOptions().position(Zoo).title("Marker Zoo"))
        mMap.addMarker(MarkerOptions().position(marker1).title("Marker końcówka"))
        mMap.addMarker(MarkerOptions().position(marker2).title("Marker skrzyżowanie"))

        val ZooMapID = R.drawable.compressed
        val ZooMap = BitmapFactory
            .decodeResource(resources, ZooMapID)

        val zooGroundOverlayOptions = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(ZooMap))
            .position(Zoo,1100F, 1100F)
        mMap.addGroundOverlay(zooGroundOverlayOptions)
        setupCamera(mMap,Zoo)

    }


    private fun setupCamera(googleMap: GoogleMap, Zoo : LatLng){
        mMap = googleMap
        val cameraPosition = CameraPosition.Builder()
            .target(Zoo)
            .zoom(20F)
            .bearing(180F)
            .build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun placeImageOnMap(ZooBorders: LatLngBounds, ZooMap : Bitmap) {
        val zooGroundOverlayOptions = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromBitmap(ZooMap))
            .positionFromBounds(ZooBorders)
        mMap.addGroundOverlay(zooGroundOverlayOptions)
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
}
