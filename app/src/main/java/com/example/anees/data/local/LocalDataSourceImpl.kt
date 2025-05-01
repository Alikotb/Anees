package com.example.anees.data.local

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.data.local.database.dao.AneesDao
import com.example.anees.data.local.database.dao.TafsirDao
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: ISharedPreferences,
    private val dao: AneesDao,
    private val tafsirDao: TafsirDao
) : LocalDataSource {
    override fun saveData(key: String, value: Any) {
        sharedPreferences.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return sharedPreferences.fetchData(key, defaultValue)
    }

    override suspend fun insertSebiha(sebiha: Sebiha) {
        dao.insertSebha(sebiha)
    }

    override fun getSebiha(): Flow<Sebiha> {
        return dao.getAllSebha()
    }

    override fun getTafsir(id: Int): Flow<TafsierModel?> {
        return tafsirDao.getAllTafsir(id)
    }

    override suspend fun insertTafsir(tafsir: TafsierModel) {
        tafsirDao.insertTafsir(tafsir)
    }
}