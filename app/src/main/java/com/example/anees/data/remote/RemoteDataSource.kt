package com.example.anees.data.remote

import com.example.anees.data.model.EditionResponse

interface RemoteDataSource {
    suspend fun getAllSections(name: String): EditionResponse
}