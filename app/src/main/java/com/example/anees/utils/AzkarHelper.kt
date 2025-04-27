package com.example.anees.utils

import android.content.Context
import com.example.anees.data.model.ZekrModelItem
import com.example.anees.data.model.adhkarItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AzkarUtils{
    fun parseAdhkar(context: Context): List<adhkarItem> {
        val jsonString = context.loadJSONFromAssets(Constants.AZKAR_FILE_NAME) ?: return emptyList()
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<adhkarItem>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getAdhkarCategories(azkarList: List<adhkarItem>): List<String> {
        return azkarList.map { it.category }.distinct()
    }

    fun getAzkarByCategory(azkarList: List<adhkarItem>, category: String): List<ZekrModelItem> {
        return azkarList
            .filter { it.category == category }
            .flatMap { it.array }
    }

}
