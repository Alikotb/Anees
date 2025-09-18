package com.example.anees.data.local

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.data.local.database.dao.AneesDao
import com.example.anees.data.local.database.dao.MahafogatDao
import com.example.anees.data.local.database.dao.TafsirDao
import com.example.anees.data.model.Ad3yaEntity
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: ISharedPreferences,
    private val dao: AneesDao,
    private val tafsirDao: TafsirDao,
    private val mahafogatDao: MahafogatDao
) : LocalDataSource {

    // --- SharedPreferences ---
    override fun saveData(key: String, value: Any) {
        sharedPreferences.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return sharedPreferences.fetchData(key, defaultValue)
    }

    // --- Sebiha ---
    override suspend fun insertSebiha(sebiha: Sebiha) {
        dao.insertSebha(sebiha)
    }

    override fun getSebiha(): Flow<Sebiha> {
        return dao.getAllSebha()
    }

    // --- Tafsir ---
    override fun getTafsir(id: Int): Flow<TafsierModel?> {
        return tafsirDao.getAllTafsir(id)
    }

    override suspend fun insertTafsir(tafsir: TafsierModel) {
        tafsirDao.insertTafsir(tafsir)
    }

    // --- Mahafogat (Saved Azkar / Ad3ya) ---
    override suspend fun insertAzkar(azkar: AzkarEntity) {
        mahafogatDao.addAzkar(azkar)
    }

    override suspend fun deleteAzkar(azkar: AzkarEntity) {
        mahafogatDao.deleteAzkar(azkar.category)
    }

    override fun getSavedAzkarFlow(): Flow<List<AzkarEntity>> {
        return mahafogatDao.getAllSavedAzkarFlow()
    }

    override suspend fun isAzkarSaved(category: String): Boolean {
        return mahafogatDao.isAzkarSaved(category)
    }

    // Ad3ya
    override suspend fun insertAd3ya(ad3ya: Ad3yaEntity) {
        mahafogatDao.addAd3ya(ad3ya)
    }

    override suspend fun deleteAd3ya(ad3ya: Ad3yaEntity) {
        mahafogatDao.deleteAd3ya(ad3ya.title)
    }

    override fun getSavedAd3yaFlow(): Flow<List<Ad3yaEntity>> {
        return mahafogatDao.getAllSavedAd3yaFlow()
    }

    override suspend fun isAd3yaSaved(title: String): Boolean {
        return mahafogatDao.isAd3yaSaved(title)
    }
}