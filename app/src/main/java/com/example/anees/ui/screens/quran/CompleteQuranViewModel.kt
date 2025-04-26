package com.example.anees.ui.screens.quran

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.repository.Repository
import com.example.anees.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log2


@HiltViewModel
class CompleteQuranViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun updateCurrentPageIndex(index: Int) {
        repository.saveData(
            key = Constants.CURRENT_PAGE_INDEX,
            value = index
        )
    }

}