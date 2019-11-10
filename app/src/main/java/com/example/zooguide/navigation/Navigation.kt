import android.util.Log
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



