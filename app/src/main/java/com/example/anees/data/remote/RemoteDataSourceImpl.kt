package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.TafsierModel
import com.example.anees.data.remote.service.HadithApiService
import com.example.anees.data.remote.service.TafsirService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val hadithApiService: HadithApiService,
    private val tafsirService: TafsirService
): RemoteDataSource {
    override suspend fun getAllSections(name: String): EditionResponse {
        return hadithApiService.getSections(name)
    }

    override suspend fun getAllTafsier(name: String): TafsierModel {
        return tafsirService.getAllTafsier(name)

    }


}