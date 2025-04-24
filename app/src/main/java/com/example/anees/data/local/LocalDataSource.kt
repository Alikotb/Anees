package com.example.anees.data.local

import com.example.anees.data.model.Sebiha
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    //SharedPreferences
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T
    suspend fun insertSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>


}