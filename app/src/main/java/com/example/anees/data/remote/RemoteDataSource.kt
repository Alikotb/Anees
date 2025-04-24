package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithsResponse

interface RemoteDataSource {
    suspend fun getAllSections(name: String): EditionResponse
    suspend fun getAuthorHadithsBySection(name: String, author: String): HadithsResponse
}