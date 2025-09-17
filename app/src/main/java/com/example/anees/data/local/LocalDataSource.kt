package com.example.anees.data.local

import com.example.anees.data.model.Ad3yaEntity
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    // --- SharedPreferences ---
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T

    // --- Sebiha ---
    suspend fun insertSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    // --- Tafsir ---
    fun getTafsir(id: Int): Flow<TafsierModel?>
    suspend fun insertTafsir(tafsir: TafsierModel)

    // --- Mahafogat (Azkar / Ad3ya) ---

    // Azkar
    suspend fun addAzkar(azkar: AzkarEntity)
    suspend fun deleteAzkar(azkar: AzkarEntity)
    suspend fun isAzkarSaved(category: String): Boolean
    fun getSavedAzkarFlow(): Flow<List<AzkarEntity>>

    // Ad3ya
    suspend fun addAd3ya(ad3ya: Ad3yaEntity)
    suspend fun deleteAd3ya(ad3ya: Ad3yaEntity)
    suspend fun isAd3yaSaved(title: String): Boolean
    fun getSavedAd3yaFlow(): Flow<List<Ad3yaEntity>>

}