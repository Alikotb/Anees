package com.example.anees.utils.PdfHelper

import android.util.Log

data class PageRangeInfo(
    val range: IntRange,
    val surahName: String,
    val juzName: String
)

val quranPageRanges = listOf(
    PageRangeInfo(568..568, "الفاتحة", "الجزء الأول"),
    PageRangeInfo(549..567, "البقرة", "الجزء الأول"),
    PageRangeInfo(531..548, "البقرة", "الجزء الثاني"),
    PageRangeInfo(506..530, "البقرة", "الجزء الثالث"),
    PageRangeInfo(505..513, "آل عمران", "الجزء الثالث"),
    PageRangeInfo(500..512, "آل عمران", "الجزء الرابع"),
    PageRangeInfo(496..499, "النساء", "الجزء الرابع"),
    PageRangeInfo(478..495, "النساء", "الجزء الخامس"),
    PageRangeInfo(474..477, "النساء", "الجزء السادس"),
    PageRangeInfo(470..473, "المائدة", "الجزء السادس"),
    PageRangeInfo(454..469, "المائدة", "الجزء السابع"),
    PageRangeInfo(441..453, "الأنعام", "الجزء السابع"),
    PageRangeInfo(433..440, "الأنعام", "الجزء الثامن"),
    PageRangeInfo(423..432, "الأعراف", "الجزء الثامن"),
    PageRangeInfo(409..422, "الأعراف", "الجزء التاسع"),
    PageRangeInfo(405..408, "الأنفال", "الجزء التاسع"),
    PageRangeInfo(400..404, "الأنفال", "الجزء العاشر"),
    PageRangeInfo(388..399, "التوبة", "الجزء العاشر"),
    PageRangeInfo(382..387, "التوبة", "الجزء الحادي عشر"),
    PageRangeInfo(370..381, "يونس", "الجزء الحادي عشر"),
    PageRangeInfo(369..369, "هود", "الجزء الحادي عشر"),
    PageRangeInfo(357..368, "هود", "الجزء الثاني عشر"),
    PageRangeInfo(351..356, "يوسف", "الجزء الثاني عشر"),
    PageRangeInfo(344..350, "يوسف", "الجزء الثالث عشر"),
    PageRangeInfo(338..343, "الرعد", "الجزء الثالث عشر"),
    PageRangeInfo(332..337, "إبراهيم", "الجزء الثالث عشر"),
    PageRangeInfo(327..331, "الحجر", "الجزء الرابع عشر"),
    PageRangeInfo(314..326, "النحل", "الجزء الرابع عشر"),
    PageRangeInfo(303..313, "الإسراء", "الجزء الخامس عشر"),
    PageRangeInfo(295..302, "الكهف", "الجزء الخامس عشر"),
    PageRangeInfo(292..294, "الكهف", "الجزء السادس عشر"),
)

fun getSurahAndJuzForPage(pageNumber: Int): Pair<String, String>? {
    Log.d("result", "getSurahAndJuzForPage: $pageNumber")
    for (rangeInfo in quranPageRanges) {
        if (pageNumber in rangeInfo.range) {
            val surahWithPrefix = "سوره ${rangeInfo.surahName}"
            return surahWithPrefix to rangeInfo.juzName
        }
    }
    return null
}
