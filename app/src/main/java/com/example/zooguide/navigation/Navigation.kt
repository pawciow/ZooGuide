import android.content.res.AssetManager
import android.util.Log
import com.example.zooguide.model.NavigationPoint
import com.example.zooguide.navigation.Dijkstra
import com.example.zooguide.navigation.DistanceCalculator
import com.example.zooguide.navigation.PreparePointsForMap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraphBuilder
import java.io.BufferedInputStream
import java.io.File
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.min


class Navigation {
    /* mutableValueGraph is a fine name for class, but not for isntacene. This is just a zoo graph*/
    private var graph: MutableValueGraph<NavigationPoint, Double> = ValueGraphBuilder
        .undirected()
        .build()
    private var preparePointsForMap: PreparePointsForMap = PreparePointsForMap()
    private var dijkstra: Dijkstra = Dijkstra()
    private var distanceCalculator: DistanceCalculator = DistanceCalculator()
    fun setUpNavigation(googleMap: GoogleMap, fileName: String, assetManager: AssetManager){
        readFileAndAddAllNodes(assetManager, fileName)
        createGraph(preparePointsForMap.points)
        putAllEdgesOnMap(googleMap)
    }

    private fun readFileAndAddAllNodes(assetManager: AssetManager, fileName: String) {
        preparePointsForMap.start(assetManager, fileName)
    }

    private fun createGraph(points: MutableList<NavigationPoint>) {
        for (point in points){
            graph.addNode(point)
        }
        for (point in points){
            addAllEdges(point, points)
        }

        for (point in points){
            if(!checkNumbersOfEdges(point))
                print("Problem z:" + point.id.toString())
        }
    }

    private fun addAllEdges(sourceNode: NavigationPoint, points: MutableList<NavigationPoint>) {
        for (connectionId in sourceNode.connections) {
            val destinationNode = points.find { it.id == connectionId }!!
            if(!graph.hasEdgeConnecting(sourceNode, destinationNode)){
                try {
                    var distance = distanceCalculator.distanceBetweenPoints(
                            sourceNode.coords,
                            destinationNode.coords
                    )

                    graph.putEdgeValue(
                        sourceNode,
                        destinationNode,
                        distance)
                } catch (e: IllegalArgumentException) {
                    Log.e("Preparations", "Error during adding edges. Arguments: "
                            + sourceNode.id.toString() + " to "
                            + destinationNode.id.toString()
                    )
                }
            }
        }
    }

    private fun checkNumbersOfEdges(node: NavigationPoint) : Boolean{
        return node.connections.size == graph.adjacentNodes(node).size
    }

    private fun putAllEdgesOnMap(googleMap: GoogleMap){
        for (point in graph.nodes()){
            googleMap.addMarker(MarkerOptions()
                .position(point.coords)
                .title(point.toString()))
        }
    }

    fun navigate(destinationNodeID: Int?): MutableList<NavigationPoint>{
        val source = preparePointsForMap.points.find { it.id == 1 }!!
        var destination : NavigationPoint
        if (destinationNodeID != null) {
            destination = preparePointsForMap.points.find { it.id == destinationNodeID }!!
        } else{
            destination = preparePointsForMap.points.find { it.id == 20 }!!
        }

         return dijkstra.calculateShortestPath(graph, source, destination)
    }
}



