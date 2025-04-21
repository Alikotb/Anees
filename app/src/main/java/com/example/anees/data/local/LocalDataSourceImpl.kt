package com.example.anees.data.local

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: ISharedPreferences,
) : LocalDataSource {
    override fun saveData(key: String, value: Any) {
        sharedPreferences.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return sharedPreferences.fetchData(key, defaultValue)
    }
}