import android.content.res.AssetManager
import android.util.Log
import com.example.zooguide.model.NavigationPoint
import com.example.zooguide.navigation.Dijkstra
import com.example.zooguide.navigation.PreparePointsForMap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.common.collect.ImmutableList
import com.google.common.graph.ImmutableValueGraph
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraphBuilder
import java.io.BufferedInputStream
import java.io.File
import java.security.AccessControlContext
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.min


class Navigation {
    /* mutableValueGraph is a fine name for class, but not for isntacene. This is just a zoo graph*/
    private var mutableValueGraph: MutableValueGraph<NavigationPoint, Double> = ValueGraphBuilder
        .undirected()
        .build()
    private var preparePointsForMap: PreparePointsForMap = PreparePointsForMap()
    private var dijkstra: Dijkstra = Dijkstra()

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
            mutableValueGraph.addNode(point)
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
            if(!mutableValueGraph.hasEdgeConnecting(sourceNode, destinationNode)){
                try {
                    /* TODO: this cannot be a part of preparePontsForMap */
                    var distance =
                        preparePointsForMap.distanceBetweenPoints(sourceNode, destinationNode)

                    mutableValueGraph.putEdgeValue(
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
        return node.connections.size == mutableValueGraph.adjacentNodes(node).size
    }

    private fun putAllEdgesOnMap(googleMap: GoogleMap){
        for (point in mutableValueGraph.nodes()){
            googleMap.addMarker(MarkerOptions()
                .position(point.coords)
                .title(point.toString()))
        }
    }

    fun navigate(){
        val source = preparePointsForMap.points.find { it.id == 1 }!!
        val destination = preparePointsForMap.points.find { it.id == 20 }!!
        dijkstra.calculateShortestPath(mutableValueGraph, source, destination)
    }
}



