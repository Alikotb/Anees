package com.example.anees.utils.names_of_allah_helper

import android.content.Context
import com.example.anees.data.model.NamesOfAllahModelItem
import com.example.anees.utils.Constants
import com.example.anees.utils.loadJSONFromAssets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun getAllNames(context: Context): List<NamesOfAllahModelItem>{
    val jsonString = context.loadJSONFromAssets(Constants.NAMES_OF_ALLAH) ?: return emptyList()
    return try {
        val gson = Gson()
        val type = object : TypeToken<List<NamesOfAllahModelItem>>() {}.type
        gson.fromJson(jsonString, type)
    } catch (e: Exception) {
        emptyList()
    }
}