package com.example.anees.data.repository

import android.util.Log
import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.model.Ad3yaEntity
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import com.example.anees.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    // --- SharedPreferences ---
    override fun saveData(key: String, value: Any) {
        localDataSource.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return localDataSource.fetchData(key,defaultValue)
    }

    // --- Sebiha ---
    override suspend fun addSebiha(sebiha: Sebiha) {
        localDataSource.insertSebiha(sebiha)
    }

    override fun getSebiha(): Flow<Sebiha> {
        return localDataSource.getSebiha()
    }

    override suspend fun getAllSections(name: String): Flow<EditionResponse> {
        return flowOf(remoteDataSource.getAllSections(name))
    }

    // --- Tafsier ---
    override suspend fun getAllTafsier(name: String): Flow<TafsierModel> {
        return flowOf(remoteDataSource.getAllTafsier(name))
    }

    override suspend fun addTafsir(tafsir: TafsierModel) {
        localDataSource.insertTafsir(tafsir)
    }

    override fun getTafsir(id: Int): Flow<TafsierModel?> {
        return localDataSource.getTafsir(id)
    }

    // --- Azkar ---
    override suspend fun insertAzkar(azkar: AzkarEntity) {
        return localDataSource.insertAzkar(azkar)
    }

    override suspend fun deleteAzkar(azkar: AzkarEntity) {
        return localDataSource.deleteAzkar(azkar)
    }

    override suspend fun isAzkarSaved(category: String): Boolean {
        return localDataSource.isAzkarSaved(category)
    }

    override fun getSavedAzkarFlow(): Flow<List<AzkarEntity>> {
        return localDataSource.getSavedAzkarFlow()
    }

    override suspend fun toggleAzkar(category: String) {
        val exists = localDataSource.isAzkarSaved(category)
        if (exists) {
            localDataSource.deleteAzkar(AzkarEntity(category = category))
        } else {
            localDataSource.insertAzkar(AzkarEntity(category = category))
        }
    }

    // --- Ad3ya ---
    override suspend fun insertAd3ya(ad3ya: Ad3yaEntity) {
        return localDataSource.insertAd3ya(ad3ya)
    }

    override suspend fun deleteAd3ya(ad3ya: Ad3yaEntity) {
        return localDataSource.deleteAd3ya(ad3ya)
    }

    override suspend fun isAd3yaSaved(title: String): Boolean {
        return localDataSource.isAd3yaSaved(title)
    }

    override fun getSavedAd3yaFlow(): Flow<List<Ad3yaEntity>> {
        return localDataSource.getSavedAd3yaFlow()
    }

    override suspend fun toggleAd3ya(title: String) {
        val exists = localDataSource.isAd3yaSaved(title)
        if (exists) {
            localDataSource.deleteAd3ya(Ad3yaEntity(title = title))
        } else {
            localDataSource.insertAd3ya(Ad3yaEntity(title = title))
        }
    }

}