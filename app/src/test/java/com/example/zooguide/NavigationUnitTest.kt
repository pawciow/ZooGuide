package com.example.zooguide

import com.example.zooguide.model.NavigationPoint
import com.example.zooguide.navigation.Dijkstra

import com.google.android.gms.maps.model.LatLng
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.junit.Assert

import org.junit.Test

import org.junit.Assert.*

class NavigationUnitTest {


}


// Testing it from the graphs from https://medium.com/basecs/finding-the-shortest-path-with-a-little-help-from-dijkstra-613149fbdc8e
class PreparePointsForMapUnitTestStub{
    val A: NavigationPoint = NavigationPoint(1, LatLng(0.00, 0.00), listOf<Int>(2,3))
    val B: NavigationPoint = NavigationPoint(2, LatLng(0.00, 0.00), listOf<Int>(1,3,4,5))
    val C: NavigationPoint = NavigationPoint(3, LatLng(0.00, 0.00), listOf<Int>(1,2,4))
    val D: NavigationPoint = NavigationPoint(4, LatLng(0.00, 0.00), listOf<Int>(2,3,5))
    val E: NavigationPoint = NavigationPoint(5, LatLng(0.00, 0.00), listOf<Int>(2,4))

    private var mutableValueGraph: MutableValueGraph<NavigationPoint, Double> = ValueGraphBuilder
        .undirected()
        .build()

    fun buildGraph(){
        mutableValueGraph.addNode(A)
        mutableValueGraph.addNode(B)
        mutableValueGraph.addNode(C)
        mutableValueGraph.addNode(D)
        mutableValueGraph.addNode(E)

        mutableValueGraph.putEdgeValue(A,B,7.0)
        mutableValueGraph.putEdgeValue(A,C,3.0)
        mutableValueGraph.putEdgeValue(B,C,1.0)
        mutableValueGraph.putEdgeValue(B,E,6.0)
        mutableValueGraph.putEdgeValue(B,D,2.0)
        mutableValueGraph.putEdgeValue(C,D,2.0)
        mutableValueGraph.putEdgeValue(D,E,4.0)
    }

    @Test
    fun testDijkstraAlgorithm(){
        val dijkstra : Dijkstra = Dijkstra()
        buildGraph()
        val expected = listOf<NavigationPoint>(A,C, D)

        val result = dijkstra.calculateShortestPath(mutableValueGraph,A,D)
        for (x in result)
        print(x.id.toString() + " ")
        println("Should be 1 3 4")

        assertEquals(expected, result)
    }
}
