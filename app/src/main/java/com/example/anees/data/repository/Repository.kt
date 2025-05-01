package com.example.anees.data.repository

import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    suspend fun getAllSections(name: String): Flow<EditionResponse>
    suspend fun getAllTafsier(name: String): Flow<TafsierModel>
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T
    suspend fun addTafsir(tafsir: TafsierModel)
    fun getTafsir(id: Int): Flow<TafsierModel?>

}