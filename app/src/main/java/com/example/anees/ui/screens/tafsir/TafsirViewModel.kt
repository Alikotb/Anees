package com.example.anees.ui.screens.tafsir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.Response
import com.example.anees.data.model.TafsierModel
import com.example.anees.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TafsirViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {
    private val _tafsirSurah = MutableStateFlow  <Response<TafsierModel>>(Response.Loading)
    val tafsirSurah = _tafsirSurah.asStateFlow()


    fun getTafsirSurah(number: String) {
        viewModelScope.launch {
            try {
                repo.getAllTafsier(number).catch {
                    _tafsirSurah.value = Response.Error(it.message.toString())
                }.collect {
                    _tafsirSurah.value = Response.Success(it)
                }
            } catch (e: Exception) {
                _tafsirSurah.value = Response.Error(e.message.toString())
            }
        }
    }
}