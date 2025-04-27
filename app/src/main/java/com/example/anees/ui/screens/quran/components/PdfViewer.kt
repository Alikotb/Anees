package com.example.anees.ui.screens.quran.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun PdfViewerFromAssets(fileName: String, startPage: Int, onPagerChange: (page: Int) -> Unit, onClicks:()-> Unit , ){
    var pdfView: PDFView? = null
    AndroidView(
        factory = { ctx ->
            PDFView(ctx, null).also { view ->
                pdfView = view
                view.fromAsset(fileName)
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .pageSnap(true)
                    .pageFling(true)
                    .defaultPage(startPage)
                    .onPageChange { page, _ ->
                        onPagerChange(page)
                        Log.d("result", "onPageChanged: $page")
                    }
                    .load()

            }
        },
        modifier = Modifier
            .fillMaxSize()
            .scale(
                scaleX = 1.05f,
                scaleY = 1.6f
            ).clickable{
                onClicks()
            }
    )
}