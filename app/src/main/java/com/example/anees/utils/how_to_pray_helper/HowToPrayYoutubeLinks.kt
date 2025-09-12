package com.example.anees.utils.how_to_pray_helper


////NOTE:Do u need to add more than one link ?, : List<String> and let user choose?
fun getHowToPrayYoutubeLink(chosenIndex: String): String {
    return when (chosenIndex) {
        "الوضوء" -> "wodoaList"
        "الصلاة" -> "prayList"
        "صلاة الجنازة" -> "funeralPrayerList"
        "صلاة الاستخارة"-> "istikharaPrayerList"
        "صلاة الاستسقاء" -> "rainPrayerList"
        "صلاة العيد" -> "https://youtu.be/gLraJZDlwOI?si=btkoxWLrftqbaikM"
        "صلاة الخوف" ->"https://youtu.be/prZ9ZrZ8J5Q?si=2PWpClOBIb4F_pSP"
        "صلاة الكسوف / الخسوف" -> "https://youtu.be/_CwuZyOH7VA?si=BWKATTPfDUOfDcSx "
        else -> ""
    }
}
