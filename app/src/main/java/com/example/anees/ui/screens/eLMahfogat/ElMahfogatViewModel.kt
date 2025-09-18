package com.example.anees.ui.screens.eLMahfogat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.repository.Repository
import com.example.anees.utils.azkar_helper.AzkarUtils
import com.example.anees.utils.hisn_almuslim_helper.HisnAlMuslimHelper
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

    private val _ad3yaTitles = MutableStateFlow<List<Pair<String, List<String>>>>(emptyList())
    val ad3yaTitles: StateFlow<List<Pair<String, List<String>>>> = _ad3yaTitles.asStateFlow()

    fun loadCategories(context: Context) {
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

    fun loadAd3ya(context: Context) {
        val ad3yaFile = "hisn/hisn_almuslim.json"
        viewModelScope.launch {
            repository.getSavedAd3yaFlow()
                .map { list -> list.map { it.title } }
                .collect { savedTitles ->
                    val titles = HisnAlMuslimHelper.getTitles(context, ad3yaFile)
                        .filter { it in savedTitles }
                        .map { title ->
                            val (texts, _) = HisnAlMuslimHelper.getSectionContent(context, ad3yaFile, title)
                            title to texts
                        }
                    _ad3yaTitles.value = titles
                }
        }
    }
}
