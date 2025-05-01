package com.example.anees.enums

import com.example.anees.R

enum class PrayEnum(val value: String , val icon: Int , val azanBackground: Int) {

    FAJR("صلاه الفجر" , R.drawable.fajr , R.drawable.fajr_azan),
    ZUHR("صلاه الظهر", R.drawable.dhuhr , R.drawable.dhuhr_azan),
    ASR("صلاه العصر", R.drawable.asr , R.drawable.asr_azan),
    MAGHRIB("صلاه المغرب", R.drawable.maghrib , R.drawable.maghrib_azan),
    ISHA("صلاه العشاء", R.drawable.isha, R.drawable.isha_azan),
}