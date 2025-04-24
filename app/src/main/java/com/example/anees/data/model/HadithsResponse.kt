package com.example.anees.data.model

data class HadithsResponse(
    val code: Int,
    val `data`: Data,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val available: Int,
        val hadiths: List<Hadith>,
        val id: String,
        val name: String,
        val requested: Int
    ) {
        data class Hadith(
            val arab: String,
            val id: String,
            val number: Int
        )
    }
}