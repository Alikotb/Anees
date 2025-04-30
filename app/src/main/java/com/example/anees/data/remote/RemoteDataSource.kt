package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.TafsierModel

interface RemoteDataSource {
    suspend fun getAllSections(name: String): EditionResponse
    suspend fun getAllTafsier(name: String): TafsierModel

}