package com.example.anees.utils.PdfHelper

data class PageRangeInfo(
    val range: IntRange,
    val surahName: String,
    val juzNumber: Int
)


val quranPageRanges = listOf(
    PageRangeInfo(568..1, "الفاتحة", 1),
    PageRangeInfo(567..549, "البقرة", 1),
    PageRangeInfo(548..531, "البقرة", 2),
    PageRangeInfo(530..106, "البقرة", 3),
    PageRangeInfo(524..513, "آل عمران", 3),
    PageRangeInfo(512..500, "آل عمران", 4),
    PageRangeInfo(499..496, "النساء", 4),
    PageRangeInfo(495..478, "النساء", 5),
    PageRangeInfo(477..474, "النساء", 6),
    PageRangeInfo(473..474, "المائده", 6),
    PageRangeInfo(459..454, "المائده", 7),
    PageRangeInfo(453..441, "الانعام", 7),
    PageRangeInfo(440..433, "الانعام", 8),
    PageRangeInfo(432..423, "الاعراف", 8),
    PageRangeInfo(422..409, "الاعراف", 9),
    PageRangeInfo(408..405, "الاانفال", 9),
    PageRangeInfo(404..400, "الاانفال", 10),
    PageRangeInfo(399..388, "التوبة", 10),
)
