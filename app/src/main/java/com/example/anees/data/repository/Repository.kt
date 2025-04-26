package com.example.anees.data.repository

import com.example.anees.data.model.Sebiha
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T

}