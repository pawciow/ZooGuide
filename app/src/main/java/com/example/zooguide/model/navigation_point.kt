package com.example.zooguide.model

import com.google.android.gms.maps.model.LatLng

data class NavigationPoint(val id : Int, val coords: LatLng, val connections : List<Int>)