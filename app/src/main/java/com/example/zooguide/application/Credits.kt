package com.example.zooguide.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import com.example.zooguide.R
import kotlinx.android.synthetic.main.activity_credits.*
import kotlinx.android.synthetic.main.activity_maps.*

class Credits : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        setupCreditsForIcons()
    }

    private fun setupCreditsForIcons(){
        tiger_author.movementMethod = LinkMovementMethod.getInstance()
        tiger_author.text = Html.fromHtml(getString(R.string.tiger_author))

        credits_author.movementMethod = LinkMovementMethod.getInstance()
        credits_author.text = Html.fromHtml(getString(R.string.credits_author))

        map_author.movementMethod = LinkMovementMethod.getInstance()
        map_author.text = Html.fromHtml(getString(R.string.map_author))
    }


    
}
