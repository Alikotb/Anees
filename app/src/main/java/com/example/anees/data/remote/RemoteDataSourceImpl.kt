package com.example.anees.data.remote

import com.example.anees.data.model.HadithBooksResponse
import com.example.anees.data.model.HadithResponse
import com.example.anees.data.model.HadithsResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val hadithApiService: HadithApiService,
): RemoteDataSource {
    override suspend fun getBooks(): HadithBooksResponse {
        return hadithApiService.getBooks()
    }

    override suspend fun getHadithsByRange(name: String, range: String): HadithsResponse {
        return hadithApiService.getHadithsByRange(name, range)
    }

    override suspend fun getSpecificHadith(name: String, number: Int): HadithResponse {
        return hadithApiService.getSpecificHadith(name, number)
    }
}