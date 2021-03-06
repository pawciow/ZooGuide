package com.example.zooguide.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.example.zooguide.R

import com.example.zooguide.R.layout.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("starting_state", " onCreate is called")
        super.onCreate(savedInstanceState)

        setContentView(activity_main)
        setupImageButtons()

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.title = getString(R.string.main_title)
        setSupportActionBar(toolbar)


    }


    private fun setupImageButtons(){
        val mapsButton: ImageButton = findViewById(R.id.Start_Navigation)
        mapsButton.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        val animalListButton: ImageButton = findViewById(R.id.Start_List)
        animalListButton.setOnClickListener{
            val intent = Intent(this, AnimalListView::class.java)
            startActivity(intent)
        }

        val creditsButton: ImageButton = findViewById(R.id.Start_Credits)
        creditsButton.setOnClickListener{
            val intent = Intent(this, Credits::class.java)
            startActivity(intent)
        }

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        Log.e("starting_state", " onCreateItemsSelected is called")
//
//        return when (item.itemId) {
//            R.id.mapa -> {
//                Log.d("MenuOptions", " Mapa")
//                val intent = Intent(this, MapsActivity::class.java)
//                startActivity(intent)
//                true
//            }
//            R.id.item2 -> {
//                Log.d("MenuOptions", " Statyczna mapa")
//                true
//            }
//            R.id.item3 -> {
//                Log.d("MenuOptions", " Item 3")
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}

