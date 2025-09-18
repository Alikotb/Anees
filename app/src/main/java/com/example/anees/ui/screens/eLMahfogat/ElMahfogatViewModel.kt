package com.example.anees.ui.screens.eLMahfogat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.repository.Repository
import com.example.anees.utils.azkar_helper.AzkarUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElMahfogatViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _azkarCategories = MutableStateFlow<List<String>>(emptyList())
    val azkarCategories: StateFlow<List<String>> = _azkarCategories.asStateFlow()

    private val _savedHadith = MutableStateFlow<List<HadithEntity>>(emptyList())
    val savedHadith: StateFlow<List<HadithEntity>> = _savedHadith.asStateFlow()

    init {
        loadSavedHadiths()
    }

    fun loadAzkarCategories(context: Context) {
        viewModelScope.launch {
            repository.getSavedAzkarFlow()
                .map { list -> list.map { it.category } }
                .collect { savedCategories ->
                    val azkarList = AzkarUtils.parseAdhkar(context)
                    val allCategories = AzkarUtils.getAdhkarCategories(azkarList)
                    _azkarCategories.value = allCategories.filter { it in savedCategories }
                }
        }
    }

    private fun loadSavedHadiths() {
        viewModelScope.launch {
            repository.getSavedHadithFlow()
                .collect { hadithList ->
                    _savedHadith.value = hadithList
                }
        }
    }

    fun toggleAzkarSaved(category: String) {
        viewModelScope.launch {
            repository.toggleAzkar(category)
        }
    }

    fun toggleHadithSaved(hadith: String) {
        viewModelScope.launch {
            repository.toggleHadith(hadith)
        }
    }
}
