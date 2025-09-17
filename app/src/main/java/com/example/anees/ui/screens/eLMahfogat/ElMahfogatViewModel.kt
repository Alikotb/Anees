package com.example.anees.ui.screens.eLMahfogat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ElMahfogatViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val savedAzkarCategories: StateFlow<Set<String>> =
        repository.getSavedAzkarFlow()
            .map { it.map { azkar -> azkar.category }.toSet() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

    val savedAd3yaTitles: StateFlow<Set<String>> =
        repository.getSavedAd3yaFlow()
            .map { it.map { ad3ya -> ad3ya.title }.toSet() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())
}
