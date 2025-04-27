package com.example.anees.data.repository

import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.Sebiha
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    suspend fun getAllSections(name: String): Flow<EditionResponse>
}