package com.example.zooguide.application

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.example.zooguide.R

import kotlinx.android.synthetic.main.activity_view_blank_map.*

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromAsset

class ViewBlankMapActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener  {
    private val TAG = MainActivity::class.java.simpleName
    val SAMPLE_FILE = "zoo_map.pdf"
    var pdfView: PDFView = null
    var pageNumber: Int = 0
    var pdfFileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_blank_map)
        setSupportActionBar(toolbar)

        val pdfView: PDFView = findViewById(R.id.pdfView)
        displayFromAsset()
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    private fun displayFromAsset(assetFileName: String) {
        pdfFileName = assetFileName

        pdfView.fromAsset(SAMPLE_FILE)
            .defaultPage(pageNumber)
            .enableSwipe(true)

            .swipeHorizontal(false)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .load()
    }

    override fun loadComplete(nbPages: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
