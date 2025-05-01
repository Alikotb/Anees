package com.example.anees.data.model

data class TafsierModel(
    val result: List<Result>
)

data class Result(
    val arabic_text: String,
    val aya: String,
    val footnotes: Any,
    val id: String,
    val sura: String,
    val translation: String
)