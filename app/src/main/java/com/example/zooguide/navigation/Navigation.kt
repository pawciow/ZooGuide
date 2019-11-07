import com.example.zooguide.model.NavigationPoint
import com.google.android.gms.maps.model.LatLng
import com.google.common.collect.ImmutableList
import com.google.common.graph.ImmutableValueGraph
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraphBuilder
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.min


class Navigation() {
    private var mutableValueGraph: MutableValueGraph<NavigationPoint, Double> = ValueGraphBuilder
        .undirected()
        .build()

    fun addAllNodes() {
        var a = mutableListOf<NavigationPoint>(
            NavigationPoint(1, LatLng(51.102021, 17.076698), listOf(123))
        )
        mutableValueGraph.addNode(a.first())

        mutableValueGraph.putEdgeValue(a.first(), a.first(), 3.0)
    }

    private fun addNodeWrapper(navigationPoint: NavigationPoint) {
        mutableValueGraph.addNode(navigationPoint)

    }
}

class Dijkstra() : Comparator<NavigationPoint>{
    private var distances : HashMap<NavigationPoint, Double> = HashMap()
    private lateinit var roadToDestination: HashMap<NavigationPoint, NavigationPoint>


    override fun compare(o1: NavigationPoint?, o2: NavigationPoint?): Int  = when{
        distances[o1]!! > distances[o2]!! -> 1
        distances[o1]!! < distances[o2]!! -> -1
        else -> 0
    }



    fun calculateShortestPath(mutableValueGraph: MutableValueGraph<NavigationPoint,Double>,
                              startingNode: NavigationPoint, endNode: NavigationPoint)
    {
        initializeSingleSource(mutableValueGraph,startingNode)

        val S =  HashSet<NavigationPoint>()

        val Q = PriorityQueue<NavigationPoint>()
        for(node in mutableValueGraph.nodes())
            Q.add(node)

        while (Q.isNotEmpty())
        {
            val u = Q.poll()
            S.add(u)
            for(node in getTwoHopNeighbors(mutableValueGraph, u))
            {
                updateDistancesFrom(mutableValueGraph, node)
            }
        }
        //TODO: Reconstruct and Print whole road!
    }

    fun initializeSingleSource(graph: MutableValueGraph<NavigationPoint, Double>,
                               sourceNode: NavigationPoint)
    {
        for (node in graph.nodes())
        {
            distances[node] = java.lang.Double.MAX_VALUE
        }

        distances[sourceNode] = 0.0
    }

    fun reconstructPath(graph: MutableValueGraph<NavigationPoint, Double>,
                        sourceNode: NavigationPoint, endNode: NavigationPoint)
    {

    }


    // SOURCE OF FUNCTIONS UNDERNEATH: https://github.com/google/guava/wiki/GraphsExplained#code-examples
    /****************************************************************************************************/
    // Return all nodes reachable by traversing 2 edges starting from "node"
    // (ignoring edge direction and edge weights, if any, and not including "node").
    fun getTwoHopNeighbors(graph: MutableValueGraph<NavigationPoint, Double>,
                           node: NavigationPoint)
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
                            node: NavigationPoint) {
        val nodeDistance = distances[node]

        for (outEdge in getTwoHopNeighbors(graph,node)) {
            val target = outEdge
            val targetDistance = nodeDistance!! +
                    graph.edgeValueOrDefault(node, outEdge, java.lang.Double.MAX_VALUE)!!

            if (targetDistance < distances.getOrDefault(target, java.lang.Double.MAX_VALUE)) {
                distances[target] = targetDistance
                roadToDestination[target] = node
            }
        }
    }
}

