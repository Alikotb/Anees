package com.example.anees.enums

import com.example.anees.data.model.RecitationModel
import com.example.anees.utils.warshList

enum class Recitations (
    val recitationName : String,
    val list : List<RecitationModel>
){
    Warsh("warsh",warshList),
    Hafs("hafs", emptyList()),
}