package com.example.zooguide.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.zooguide.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("starting_state", " onCreate is called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.e("starting_state", " onCreateOptionsMenu is called")

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    fun startMapsActivity(view: View){
//        val intent = Intent(baseContext, MapsActivity::class.java).apply {}
//        startActivity(intent)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("starting_state", " onCreateItemsSelected is called")

        return when (item.itemId) {
            R.id.mapa -> {
                Log.d("MenuOptions", " Mapa")
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item2 -> {
                Log.d("MenuOptions", " Statyczna mapa")
                true
            }
            R.id.item3 -> {
                Log.d("MenuOptions", " Item 3")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

