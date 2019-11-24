package com.example.zooguide

import com.example.zooguide.model.NavigationPoint
import com.example.zooguide.contextualInformation.CompassManager
import com.example.zooguide.contextualInformation.Direction
import com.google.android.gms.maps.model.LatLng
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CompassManagerUnitTests {
    @Test
    fun addition_isCorrect() {
        val correctAnswer = mutableListOf<Direction?>()
//        correctAnswer.add(Direction.Ahead, Direction.Right, Direction.BackwardRight, Direction.Backward, Direction.BackwardLeft, Direction.Left)
        correctAnswer.add(Direction.Ahead)
        correctAnswer.add(Direction.Right)
        val manager: CompassManager =
            CompassManager(LatLng(0.0, 0.0))

        val points = mutableListOf<NavigationPoint>()
        val navPoint1 = NavigationPoint(1, LatLng(10.0, 0.0), mutableListOf())
        val navPoint2 = NavigationPoint(2, LatLng(10.0, 10.0), mutableListOf())
        points.add(navPoint1)
        points.add(navPoint2)

        assertEquals(correctAnswer, manager.getAllPoints(points))

        assertEquals(4, 2 + 2)

    }

//    @Test
//    fun calculatingBearing_isCorrect(){
//        val correctAnswer = mutableListOf<Direction?>()
////        correctAnswer.add(Direction.Ahead, Direction.Right, Direction.BackwardRight, Direction.Backward, Direction.BackwardLeft, Direction.Left)
//        correctAnswer.add(Direction.Ahead)
//        correctAnswer.add(Direction.Right)
//        val manager: CompassManager = CompassManager(LatLng(0.0, 0.0))
//
//        val points = mutableListOf<NavigationPoint>()
//        val navPoint1 = NavigationPoint(1, LatLng(10.0, 0.0), mutableListOf())
//        val navPoint2 = NavigationPoint(2, LatLng(10.0, 10.0), mutableListOf())
//        points.add(navPoint1)
//        points.add(navPoint2)
//
//        assertEquals(correctAnswer, manager.getAllPoints(points))
//    }
}

