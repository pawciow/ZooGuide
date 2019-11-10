package com.example.zooguide

import com.example.zooguide.model.NavigationPoint
import org.junit.Test
import com.example.zooguide.navigation.PreparePointsForMap
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


class PreparePointsForMapUnitTest {
    var preparePointsForMap: PreparePointsForMap = PreparePointsForMap()


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun createPoints_isCorrect() {
        val expected = NavigationPoint(
            id = 1,
            coords = LatLng(51.0, 51.0),
            connections = listOf(1, 2, 3)
        )
        var parameter = listOf<String>("1", "51.0", "51.0", "1,2,3")

        assertEquals(expected, preparePointsForMap.createPoints(parameter))
    }
}
