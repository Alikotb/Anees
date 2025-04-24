package com.example.anees.data.repository

import com.example.anees.data.model.HadithBooksResponse
import com.example.anees.data.model.HadithResponse
import com.example.anees.data.model.HadithsResponse
import com.example.anees.data.model.Sebiha
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    suspend fun getBooks(): Flow<HadithBooksResponse>
    suspend fun getHadithsByRange(name: String, range: String): Flow<HadithsResponse>
    suspend fun getSpecificHadith(name: String, number: Int): Flow<HadithResponse>
}