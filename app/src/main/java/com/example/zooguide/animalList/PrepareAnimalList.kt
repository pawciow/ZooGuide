package com.example.zooguide.animalList

import android.content.res.AssetManager
import android.util.Log
import com.example.zooguide.model.Animal
import com.example.zooguide.model.Event
import com.google.android.gms.maps.model.LatLng
import java.io.*
import java.util.*

class PrepareAnimalList {
    private var animals: MutableList<Animal> = mutableListOf()

    public fun start(assetManager: AssetManager, fileName: String): MutableList<Animal> {
        readLinesAndFetchPoints(assetManager, fileName)
        return animals
    }

    private fun readLinesAndFetchPoints(assetManager: AssetManager, fileName: String) {
        var buffer: BufferedReader? = null
        try {
            buffer = assetManager.open(fileName).bufferedReader()
            buffer.use {
                it.forEachLine {
                    val parts = parseLine(it)
                    val point = createAnimal(parts)
                    animals.add(point)
                }
            }
        } catch (e: IOException) {
            Log.e("Preparation", " CANT READ LINES FROM ASSETS")
        } finally {
            if (buffer != null) {
                try {
                    buffer.close()
                } catch (e: IOException) {
                    Log.e("Preparation", " CANT CLOSE THE FILE BUFFER")
                }
            }
        }
    }

    private fun parseLine(string: String): List<String> {
        return string.split(";")
    }

    fun createAnimal(parts: List<String>): Animal {
        return Animal(
            parts[0].toInt(),
            parts[2],
            parts[1],
            LatLng(
                parts[3].toDouble(),
                parts[4].toDouble()
            ),
            parts[5]
        )
    }
}