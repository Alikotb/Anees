package com.example.anees.data.local

interface LocalDataSource {
    //SharedPreferences
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T
}