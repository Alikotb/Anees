package com.example.anees.data.model


import com.google.gson.annotations.SerializedName

data class HadithResponse(
    val code: Int,
    val `data`: Data,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val available: Int,
        val contents: Contents,
        val id: String,
        val name: String
    ) {
        data class Contents(
            val arab: String,
            val id: String,
            val number: Int
        )
    }
}