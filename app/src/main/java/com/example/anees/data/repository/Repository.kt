package com.example.anees.data.repository

import com.example.anees.data.model.Ad3yaEntity
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Sebiha
    suspend fun addSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    // --- Sections (Remote) ---
    suspend fun getAllSections(name: String): Flow<EditionResponse>

    // SharedPreferences
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T

    // Tafsir
    suspend fun getAllTafsier(name: String): Flow<TafsierModel>
    suspend fun addTafsir(tafsir: TafsierModel)
    fun getTafsir(id: Int): Flow<TafsierModel?>


    // --- Azkar ---
    suspend fun insertAzkar(azkar: AzkarEntity)
    suspend fun deleteAzkar(azkar: AzkarEntity)
    suspend fun isAzkarSaved(category: String): Boolean
    suspend fun toggleAzkar(category: String)
    fun getSavedAzkarFlow(): Flow<List<AzkarEntity>>

    // --- Ad3ya ---
    suspend fun insertAd3ya(ad3ya: Ad3yaEntity)
    suspend fun deleteAd3ya(ad3ya: Ad3yaEntity)
    suspend fun isAd3yaSaved(title: String): Boolean
    suspend fun toggleAd3ya(title: String)
    fun getSavedAd3yaFlow(): Flow<List<Ad3yaEntity>>
}