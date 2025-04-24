package com.example.anees.data.remote

import com.example.anees.data.model.HadithBooksResponse
import com.example.anees.data.model.HadithResponse
import com.example.anees.data.model.HadithsResponse

interface RemoteDataSource {
    suspend fun getBooks(): HadithBooksResponse
    suspend fun getHadithsByRange(name: String, range: String): HadithsResponse
    suspend fun getSpecificHadith(name: String, number: Int): HadithResponse
}