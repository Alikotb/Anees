package com.example.anees.ui.screens.quran.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun PdfViewerFromAssets(fileName: String , onClicks:()-> Unit){
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
                    .defaultPage(474)
                    .onPageChange { page, _ ->
                        Log.d("TAG", "PdfViewerFromAssets: ${page}")
                    }
                    .load()

            }
        },
        modifier = Modifier
            .fillMaxSize()
            .scale(
                scaleX = 1.05f,
                scaleY = 1.6f
            )
    )
}