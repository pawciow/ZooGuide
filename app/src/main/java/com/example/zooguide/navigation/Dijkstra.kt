@file:Suppress("UnstableApiUsage")

package com.example.zooguide.navigation

import com.example.zooguide.model.NavigationPoint
import com.google.common.graph.MutableValueGraph
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class Dijkstra{

    private var distances : HashMap<NavigationPoint, Double> = HashMap()
    private var previous : HashMap<NavigationPoint, NavigationPoint> = HashMap()

    fun calculateShortestPath(graph: MutableValueGraph<NavigationPoint, Double>,
                              startingNode: NavigationPoint, endNode: NavigationPoint
    ) : MutableList<NavigationPoint> {
        initializeSingleSource(graph, startingNode)

        val s = HashSet<NavigationPoint>()

        val q = HashSet<NavigationPoint>()
        for(node in graph.nodes())
            q.add(node)

        while (q.isNotEmpty())
        {
            val u = extractMin(q)
            println("LEAST ELEMENT: " + u.id.toString() + " with distance: " + distances[u].toString())

            q.remove(u)
            s.add(u)

            updateDistancesFrom(graph, u)
        }

        return reconstructPath(
            sourceNode = startingNode,
            endNode = endNode
        )
    }

    private fun initializeSingleSource(graph: MutableValueGraph<NavigationPoint, Double>,
                                       sourceNode: NavigationPoint){
        for (node in graph.nodes())
        {
            distances[node] = java.lang.Double.MAX_VALUE
        }

        distances[sourceNode] = 0.0
    }

    private fun reconstructPath(sourceNode: NavigationPoint, endNode: NavigationPoint)
            :MutableList<NavigationPoint>{
        val path : MutableList<NavigationPoint> = mutableListOf()

        println("Path from " + sourceNode.id + " to " + endNode.id)

        path.add(endNode)
        var it : NavigationPoint = previous[endNode]!!
        while (it != sourceNode) {
            println("Adding " + it.id)
            path.add(it)
            it = previous[it]!!
        }
        path.add(sourceNode)
        return path
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

    // SOURCE OF FUNCTIONS UNDERNEATH: https://github.com/google/guava/wiki/GraphsExplained#code-examples
    /****************************************************************************************************/
    private fun updateDistancesFrom(graph: MutableValueGraph<NavigationPoint, Double>,
                            node: NavigationPoint) {
        val nodeDistance = distances[node]

        for (outEdge in graph.adjacentNodes(node)) {
            val targetDistance = nodeDistance!! +
                    graph.edgeValueOrDefault(node, outEdge, java.lang.Double.MAX_VALUE)!!

            if (targetDistance < distances[outEdge]!!) {
                println("Going from: " + node.id + " to: " + outEdge.id)
                distances[outEdge] = targetDistance
                previous[outEdge] = node
            }
        }
    }
}