package com.example.zooguide.model

import com.google.android.gms.maps.model.LatLng

data class Animal(val id: Int, val imageUrl: String, val name: String?,val coords: LatLng, val description: String?)