package com.example.anees.data.model


data class HadithBooksResponse(
    val code: Int,
    val `data`: List<Data>,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val available: Int,
        val id: String,
        val name: String
    )
}