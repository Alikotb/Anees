package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val hadithApiService: HadithApiService,
): RemoteDataSource {
    override suspend fun getAllSections(name: String): EditionResponse {
        return hadithApiService.getSections(name)
    }


}