package com.example.anees.ui.screens.hadith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithsResponse
import com.example.anees.data.model.Response
import com.example.anees.data.repository.RepositoryImpl
import com.example.anees.utils.hadith_helper.AuthorEdition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {
    private val _sectionsState = MutableStateFlow<Response<EditionResponse>>(Response.Loading)
    val sectionsState = _sectionsState.asStateFlow()

    private val _authorHadithsState = MutableStateFlow<Response<HadithsResponse>>(Response.Loading)
    val authorHadithsState = _authorHadithsState.asStateFlow()

    private val _messageState = MutableStateFlow<String?>(null)
    val messageState = _messageState.asStateFlow()


    fun getSections(author: AuthorEdition) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getAllSections(author.apiKey)
                    .catch {
                        _sectionsState.value = Response.Error(it.message.toString())
                    }
                    .collect {
                        _sectionsState.value = Response.Success(it)
                    }
            }catch (e: Exception){
                _messageState.value = e.message
            }
        }

    }

    fun getAuthorHadithsBySection(sectionName: String, author: AuthorEdition) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAuthorHadithsBySection(sectionName, author.apiKey)
                .catch {
                    _authorHadithsState.value = Response.Error(it.message.toString())
                }
                .collect {
                    _authorHadithsState.value = Response.Success(it)
                }
        }
    }
}