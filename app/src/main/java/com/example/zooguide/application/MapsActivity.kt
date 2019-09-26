package com.example.zooguide.application

import android.content.Context
import android.content.res.Resources
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
        mMap.addMarker(MarkerOptions().position(Zoo).title("Marker Zoo"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Zoo))

//        val zooMapID = R.drawable.mapa_zoo_wroclaw
//        val zooMap : Bitmap = BitmapFactory.decodeResource(resources, zooMapID)
//        val zooGroundOverlayOptions = GroundOverlayOptions().image(BitmapDescriptorFactory.fromBitmap(zooMap))
//        mMap.addGroundOverlay(zooGroundOverlayOptions)
    }
}
