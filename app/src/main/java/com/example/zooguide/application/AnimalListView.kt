package com.example.zooguide.application

import android.content.res.AssetManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.zooguide.R
import com.example.zooguide.animalList.MySimpleArrayAdapter
import com.example.zooguide.model.NavigationPoint
import com.example.zooguide.navigation.PreparePointsForMap

import kotlinx.android.synthetic.main.activity_animal_list_view.*

class AnimalListView : AppCompatActivity() {
    private var preparePointsForMap: PreparePointsForMap = PreparePointsForMap()


    private lateinit var listView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_list_view)
        setSupportActionBar(toolbar)


        var assetManager: AssetManager = applicationContext.assets

        listView = findViewById(R.id.recipe_list_view)
// 1
        val animalList
                = preparePointsForMap.start(assetManager, getString(R.string.point_list))
// 2
        val listItems = arrayOfNulls<String>(animalList.size)
// 3
        for (i in 0 until animalList.size) {
            val animal = animalList[i]
            listItems[i] = animal.id.toString()
        }
        val adapter = MySimpleArrayAdapter(this, animalList as ArrayList<NavigationPoint>)
        listView.adapter = adapter

//// 4
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
//        listView.adapter = adapter

    }

}