package com.example.zooguide.application

import com.example.zooguide.navigation.Navigation
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.location.Location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.zooguide.R
import com.example.zooguide.animalList.PrepareAnimalList
import com.example.zooguide.contextualInformation.CompassManager
import com.example.zooguide.contextualInformation.EventManager
import com.example.zooguide.map.MapSetup
import com.example.zooguide.model.Animal
import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var navigation: Navigation
    private lateinit var mapSetup : MapSetup
    private lateinit var eventManager: EventManager
    private lateinit var compassManager: CompassManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        Log.e("starting_state", " MAPS IS CREATED< IT SHOULD WORK")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val assetManager: AssetManager = applicationContext.assets

        mapSetup = MapSetup()
        mMap = googleMap
        navigation = Navigation()
        eventManager = EventManager()


        val zoo = LatLng(51.104210, 17.07446)
        val zooMapID = R.drawable.compressed
        val zooMap = BitmapFactory.decodeResource(resources, zooMapID)

        mapSetup.setupCamera(mMap, zoo)
        mapSetup.placeImageOnMap(mMap, zoo, zooMap)

        setUpEventManager(assetManager)

        setUpGPS()
        navigation.setUpNavigation(mMap, getString(R.string.point_list), assetManager)

        val toFind = intent.extras?.get(EXTRA_COORDS) as LatLng?
        if (toFind != null){
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null){
                            val route = navigation.navigate(location, toFind)
                            mMap.addPolyline(mapSetup.getPolyLine(route))
                    }
             }
        }
    }

    private fun setUpGPS() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                print("Explanation to the user")

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
        }
    }

    private fun setUpEventManager(assetManager: AssetManager){
        eventManager.setupEvents(assetManager, getString(R.string.eventList))
        eventManager.startRepeatingTask(applicationContext)
    }

    override fun onMyLocationClick(location: Location) {
        compassManager = CompassManager(location)
        val assetManager: AssetManager = applicationContext.assets


        val preparation = PrepareAnimalList()
        val points = preparation.start(assetManager, getString(R.string.animal_description_list))
        val result = compassManager.getAllPoints(points)
        Toast.makeText(this, "Znalazłem : $result", Toast.LENGTH_LONG).show()
    }
    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }



    //FOR LISTVIEW
    companion object {
        private const val EXTRA_COORDS = "id"

        fun newIntent(context: Context, destination: Animal): Intent {
            val detailIntent = Intent(context, MapsActivity::class.java)

            detailIntent.putExtra(EXTRA_COORDS, destination.coords)

            return detailIntent
        }
    }
}
