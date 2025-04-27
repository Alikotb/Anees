package com.example.anees.ui.screens.quran_pdf.quran_index

import androidx.lifecycle.ViewModel
import com.example.anees.data.repository.Repository
import com.example.anees.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class QuranIndexViewModel @Inject constructor(
    private  val repository: Repository ) : ViewModel() {
      fun updateCurrentPageIndex(index: Int) {
          repository.saveData(
              key = Constants.CURRENT_PAGE_INDEX,
              value = index
          )
      }
}