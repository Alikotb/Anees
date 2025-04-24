package com.example.anees.ui.screens.hadith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.HadithBooksResponse
import com.example.anees.data.model.HadithResponse
import com.example.anees.data.model.HadithsResponse
import com.example.anees.data.model.Response
import com.example.anees.data.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class HadithViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {
    private val _booksState = MutableStateFlow<Response<HadithBooksResponse>>(Response.Loading)
    val booksState  = _booksState.asStateFlow()

    private val _hadithsState = MutableStateFlow<Response<HadithsResponse>>(Response.Loading)
    val hadithsState = _hadithsState.asStateFlow()

    private val _specificHadithState = MutableStateFlow<Response<HadithResponse>>(Response.Loading)
    val specificHadithState = _specificHadithState.asStateFlow()

    private val _messageState = MutableStateFlow<String?>(null)
    val messageState = _messageState.asStateFlow()

    fun getBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBooks().catch {
                _booksState.value = Response.Error(it.message.toString())
            }.collect {
                _booksState.value = Response.Success(it)
            }
        }
    }

    fun getHadithsByRange(bookName: String, range: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getHadithsByRange(bookName, range).catch{
                _hadithsState.value = Response.Error(it.message.toString())
            }.collect {
                _hadithsState.value = Response.Success(it)
            }
        }
    }

    fun getSpecificHadith(bookName: String, hadithNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificHadith(bookName, hadithNumber).catch {
                _specificHadithState.value = Response.Error(it.message.toString())
            }.collect {
                _specificHadithState.value = Response.Success(it)
            }
        }
    }
}