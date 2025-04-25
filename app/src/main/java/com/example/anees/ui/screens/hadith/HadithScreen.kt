package com.example.anees.ui.screens.hadith

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anees.data.model.Response

@Composable
fun HadithScreen(viewModel: HadithViewModel = viewModel()) {

    val sectionsState by viewModel.sectionsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
    }

    when(val state = sectionsState) {
        is Response.Loading -> TODO()
        is Response.Success -> TODO()
        is Response.Error -> TODO()
    }
}