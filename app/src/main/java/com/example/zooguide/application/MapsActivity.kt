package com.example.zooguide.application

import Navigation
import android.content.res.AssetManager
import android.graphics.BitmapFactory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.zooguide.R
import com.example.zooguide.map.MapSetup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*




class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var navigation: Navigation
    private lateinit var mapSetup : MapSetup

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
        var assetManager: AssetManager = applicationContext.assets

        mapSetup = MapSetup()
        mMap = googleMap
        navigation = Navigation()
        val Zoo = LatLng(51.104210, 17.07446)
        val ZooMapID = R.drawable.compressed
        val ZooMap = BitmapFactory.decodeResource(resources, ZooMapID)

        mapSetup.setupCamera(mMap,Zoo)
        mapSetup.placeImageOnMap(mMap,Zoo, ZooMap)
        navigation.setUpNavigation(mMap, getString(R.string.point_list), assetManager)

        navigation.navigate()
    }
}
