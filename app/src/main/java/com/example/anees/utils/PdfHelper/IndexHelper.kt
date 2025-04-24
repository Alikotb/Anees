package com.example.anees.utils.PdfHelper

import com.example.anees.Enums.SuraTypeEnum

data class SuraIndex(
    val index : Int ,
    val start: Int,
    val suraName : String,
    val type : SuraTypeEnum,
    val pageNumber : Int,
    val ayahNumber : Int ,

)

val SuraIndexes = listOf(
    SuraIndex(1, 1, "الفاتحة", SuraTypeEnum.MECCA, 568, 7),
    SuraIndex(2, 2, "البقرة", SuraTypeEnum.MADIENA, 567, 286),
    SuraIndex(3, 293, "آل عمران", SuraTypeEnum.MADIENA, 513, 200),
    SuraIndex(4, 493, "النساء", SuraTypeEnum.MADIENA, 499, 176),
    SuraIndex(5, 669, "المائدة", SuraTypeEnum.MADIENA, 473, 120),
    SuraIndex(6, 786, "الأنعام", SuraTypeEnum.MECCA, 453, 165),

    SuraIndex(7, 951, "الأعراف", SuraTypeEnum.MECCA, 7, 206),
    SuraIndex(8, 1157, "الأنفال", SuraTypeEnum.MADIENA, 8, 75),
    SuraIndex(9, 1256, "التوبة", SuraTypeEnum.MADIENA, 9, 129),
    SuraIndex(10, 1385, "يونس", SuraTypeEnum.MECCA, 10, 109),
    SuraIndex(11, 1494, "هود", SuraTypeEnum.MECCA, 11, 123),
    SuraIndex(12, 1617, "يوسف", SuraTypeEnum.MECCA, 12, 111),
    SuraIndex(13, 1732, "إبراهيم", SuraTypeEnum.MECCA, 13, 52),
    SuraIndex(14, 1854, "الحجر", SuraTypeEnum.MECCA, 14, 99),
    SuraIndex(15, 1953, "النحل", SuraTypeEnum.MECCA, 15, 128),
    SuraIndex(16, 2061, "الإسراء", SuraTypeEnum.MECCA, 16, 111),
    SuraIndex(17, 2181, "الكهف", SuraTypeEnum.MECCA, 17, 110),
    SuraIndex(18, 2291, "مريم", SuraTypeEnum.MECCA, 18, 98),
    SuraIndex(19, 2391, "طه", SuraTypeEnum.MECCA, 19, 135),
    SuraIndex(20, 2503, "الأنبياء", SuraTypeEnum.MECCA, 20, 112),
    SuraIndex(21, 2623, "الحج", SuraTypeEnum.MADIENA, 21, 78),
    SuraIndex(22, 2741, "المؤمنون", SuraTypeEnum.MECCA, 22, 118),
    SuraIndex(23, 2860, "الفرقان", SuraTypeEnum.MECCA, 23, 77),
    SuraIndex(24, 2981, "النمل", SuraTypeEnum.MECCA, 24, 55),
    SuraIndex(25, 3100, "الفرقان", SuraTypeEnum.MECCA, 25, 77),
    SuraIndex(26, 3219, "الشعراء", SuraTypeEnum.MECCA, 26, 227),
    SuraIndex(27, 3338, "النمل", SuraTypeEnum.MECCA, 27, 55),
    SuraIndex(28, 3463, "القصص", SuraTypeEnum.MECCA, 28, 88),
    SuraIndex(29, 3572, "العنكبوت", SuraTypeEnum.MECCA, 29, 69),
    SuraIndex(30, 3693, "الروم", SuraTypeEnum.MECCA, 30, 60),
    SuraIndex(31, 3803, "لقمان", SuraTypeEnum.MECCA, 31, 34),
    SuraIndex(32, 3920, "السجدة", SuraTypeEnum.MECCA, 32, 30),
    SuraIndex(33, 4021, "الأحزاب", SuraTypeEnum.MADIENA, 33, 73),
    SuraIndex(34, 4132, "سبأ", SuraTypeEnum.MECCA, 34, 54),
    SuraIndex(35, 4245, "فاطر", SuraTypeEnum.MECCA, 35, 45),
    SuraIndex(36, 4356, "يس", SuraTypeEnum.MECCA, 36, 83),
    SuraIndex(37, 4469, "الصافات", SuraTypeEnum.MECCA, 37, 182),
    SuraIndex(38, 4587, "ص", SuraTypeEnum.MECCA, 38, 88),
    SuraIndex(39, 4700, "الزمر", SuraTypeEnum.MECCA, 39, 75),
    SuraIndex(40, 4824, "غافر", SuraTypeEnum.MECCA, 40, 85),
    SuraIndex(41, 4945, "فصلت", SuraTypeEnum.MECCA, 41, 54),
    SuraIndex(42, 5066, "الشورى", SuraTypeEnum.MECCA, 42, 53),
    SuraIndex(43, 5183, "الزخرف", SuraTypeEnum.MECCA, 43, 89),
    SuraIndex(44, 5304, "الدخان", SuraTypeEnum.MECCA, 44, 59),
    SuraIndex(45, 5421, "الجاثية", SuraTypeEnum.MECCA, 45, 37),
    SuraIndex(46, 5535, "الأحقاف", SuraTypeEnum.MECCA, 46, 35),
    SuraIndex(47, 5650, "محمد", SuraTypeEnum.MADIENA, 47, 38),
    SuraIndex(48, 5765, "الفتح", SuraTypeEnum.MADIENA, 48, 29),
    SuraIndex(49, 5875, "الحجرات", SuraTypeEnum.MADIENA, 49, 18),
    SuraIndex(50, 5992, "ق", SuraTypeEnum.MECCA, 50, 45),
    // Add remaining Surahs similarly
)

