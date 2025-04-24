package com.example.anees.utils

enum class AuthorEdition(
    val apiKey: String,
    val displayNameEn: String,
    val displayNameAr: String
) {
    ABU_DAWUD("abudawud", "Abu Dawud", "أبو داود"),
    BUKHARI("bukhari", "Imam Bukhari", "الإمام البخاري"),
    DEHLAWI("dehlawi", "Shah Waliullah Dehlawi", "شاه ولي الله الدهلوي"),
    IBN_MAJAH("ibnmajah", "Ibn Majah", "ابن ماجه"),
    MALIK("malik", "Imam Malik", "الإمام مالك"),
    MUSLIM("muslim", "Imam Muslim", "الإمام مسلم"),
    NASAI("nasai", "An-Nasā’ī", "النسائي"),
    NAWAWI("nawawi", "Imam Nawawi", "الاربعين النوويه"),
    QUDSI("qudsi", "Hadith Qudsi", "الحديث القدسي"),
    TIRMIDHI("tirmidhi", "At-Tirmidhi", "الترمذي");

    companion object {
        fun fromApiKey(key: String): AuthorEdition? =
            entries.find { it.apiKey == key }
    }
}
