package com.example.zooguide.navigation

import android.util.Log
import com.example.zooguide.model.NavigationPoint
import com.google.common.graph.MutableValueGraph
import java.util.*
import kotlin.Comparable
import kotlin.Comparator
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class Dijkstra{

    private var distances : HashMap<NavigationPoint, Double> = HashMap()
    private  var roadToDestination: HashMap<NavigationPoint, NavigationPoint> = HashMap()


    public fun printAllHashMaps(){
        for(navPoint in distances)
        {

        }

    }

    fun calculateShortestPath(mutableValueGraph: MutableValueGraph<NavigationPoint, Double>,
                              startingNode: NavigationPoint, endNode: NavigationPoint
    )
            : List<NavigationPoint>
    {
        initializeSingleSource(mutableValueGraph,startingNode)

        val S =  HashSet<NavigationPoint>()
        var Q = HashSet<NavigationPoint>()

//        var Q = PriorityQueue(
//            Comparator<NavigationPoint> { o1, o2 ->
//            when{
//            distances[o1]!! > distances[o2]!! -> 1
//            distances[o1]!! < distances[o2]!! -> -1
//            else -> 0
//            }}
//        )


        for(node in mutableValueGraph.nodes())
            Q.add(node)

        while (Q.isNotEmpty())
        {
//            println("PRINTING QUEUE:")
//            for(item in Q)
//                debugPrint(item)
            println("LEAST ELEMENT: " + extractMin(Q).id.toString() + " with distance: " + distances[extractMin(Q)].toString())

            val u =
                Q.remove(extractMin(Q))
            S.add(u)

            updateDistancesFrom(mutableValueGraph, u)


        }

        return reconstructPath(
            sourceNode = startingNode,
            graph = mutableValueGraph,
            endNode = endNode
        )
    }

    private fun initializeSingleSource(graph: MutableValueGraph<NavigationPoint, Double>,
                                       sourceNode: NavigationPoint
    )
    {
        for (node in graph.nodes())
        {
            distances[node] = java.lang.Double.MAX_VALUE
        }

        distances[sourceNode] = 0.0
    }

    private fun reconstructPath(graph: MutableValueGraph<NavigationPoint, Double>,
                                sourceNode: NavigationPoint, endNode: NavigationPoint
    )
            :List<NavigationPoint>
    {
        val path : HashSet<NavigationPoint> = hashSetOf()

        println("Path from " + sourceNode.id + " to " + endNode.id)

        path.add(endNode)
        var it : NavigationPoint = roadToDestination[endNode]!!
        while (it != sourceNode) {
            println("Adding " + it.id)
            path.add(it)
            it = roadToDestination[it]!!
        }
        path.add(sourceNode)
        return path.toList()
    }

    private fun extractMin(nodes : HashSet<NavigationPoint>) : NavigationPoint{
        var soFarSmallest : NavigationPoint = nodes.first()
        for (node in nodes) {
            if (distances[soFarSmallest]!! > distances[node]!!){
                soFarSmallest = node
            }
        }


        return soFarSmallest

    }

    fun debugPrint(navigationPoint: NavigationPoint){
        println("Node: " + navigationPoint.id + " and its distance " + distances[navigationPoint].toString())
    }

    // SOURCE OF FUNCTIONS UNDERNEATH: https://github.com/google/guava/wiki/GraphsExplained#code-examples
    /****************************************************************************************************/
    // Return all nodes reachable by traversing 2 edges starting from "node"
    // (ignoring edge direction and edge weights, if any, and not including "node").
    fun getTwoHopNeighbors(graph: MutableValueGraph<NavigationPoint, Double>,
                           node: NavigationPoint
    )
            : Set<NavigationPoint> {
        val twoHopNeighbors = HashSet<NavigationPoint>()
        for (neighbor in graph.adjacentNodes(node)) {
            twoHopNeighbors.addAll(graph.adjacentNodes(neighbor))
        }
        twoHopNeighbors.remove(node)
        return twoHopNeighbors
    }

    // Update the shortest-path weighted distances of the successors to "node"
    // in a directed Network (inner loop of Dijkstra's algorithm)
    // given a known distance for {@code node} stored in a {@code Map<N, Double>},
    // and a {@code Function<E, Double>} for retrieving a weight for an edge.
    fun updateDistancesFrom(graph: MutableValueGraph<NavigationPoint, Double>,
                            node: NavigationPoint
    ) {
        val nodeDistance = distances[node]

        for (outEdge in getTwoHopNeighbors(graph,node)) {
            val target = outEdge
            val targetDistance = nodeDistance!! +
                    graph.edgeValueOrDefault(node, outEdge, java.lang.Double.MAX_VALUE)!!

            if (targetDistance < distances.getOrDefault(target, java.lang.Double.MAX_VALUE)) {
                println("Going from: " + node.id + " to: " + target.id)
                distances[target] = targetDistance
                roadToDestination[target] = node
            }
        }
    }
}