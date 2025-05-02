package com.example.anees.utils.prayer_helper


import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.example.anees.enums.PrayEnum
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.extensions.toArabicTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object PrayerTimesHelper {

    fun getNextPrayer(): Pair<PrayEnum, Long>? {
        val coordinates = Coordinates(30.666733, 31.169271)
        val dateComponents = DateComponents.from(Date())

        val params = CalculationMethod.EGYPTIAN.parameters
        //params.madhab = Madhab.SHAFI

        val prayerTimes = PrayerTimes(coordinates, dateComponents, params)

        val now = System.currentTimeMillis()

        val prayers = listOf(
            PrayEnum.FAJR to prayerTimes.fajr,
            PrayEnum.ZUHR to prayerTimes.dhuhr,
            PrayEnum.ASR to prayerTimes.asr,
            PrayEnum.MAGHRIB to prayerTimes.maghrib,
            PrayEnum.ISHA to prayerTimes.isha
        )

        for ((prayerEnum, date) in prayers) {
            val timeMillis = date.time
            if (timeMillis > now) {
                return Pair(prayerEnum, timeMillis)
            }
        }

        val tomorrow = Calendar.getInstance().apply { add(Calendar.DATE, 1) }
        val tomorrowDate = DateComponents.from(tomorrow.time)
        val tomorrowPrayerTimes = PrayerTimes(coordinates, tomorrowDate, params)

        val fajrMillis = tomorrowPrayerTimes.fajr.time

        return Pair(PrayEnum.FAJR, fajrMillis)
    }

    fun getAllPrayers(): List<Triple<PrayEnum, Long, Boolean>> {
        val coordinates = Coordinates(30.666733, 31.169271)
        val dateComponents = DateComponents.from(Date())

        val params = CalculationMethod.EGYPTIAN.parameters
        //params.madhab = Madhab.SHAFI

        val prayerTimes = PrayerTimes(coordinates, dateComponents, params)

        return listOf(

            Triple(PrayEnum.FAJR, prayerTimes.fajr.time,isPrayerTime(PrayEnum.FAJR)),
            Triple(PrayEnum.ZUHR, prayerTimes.dhuhr.time, isPrayerTime(PrayEnum.ZUHR)),
            Triple(PrayEnum.ASR, prayerTimes.asr.time, isPrayerTime(PrayEnum.ASR)),
            Triple(PrayEnum.MAGHRIB, prayerTimes.maghrib.time, isPrayerTime(PrayEnum.MAGHRIB)),
            Triple(PrayEnum.ISHA, prayerTimes.isha.time, isPrayerTime(PrayEnum.ISHA))
        )
    }

    fun getUpcomingPrayers(): List<Pair<PrayEnum, Long>> {
        val coordinates = Coordinates(30.666733, 31.169271)
        val now = System.currentTimeMillis()

        val result = mutableListOf<Pair<PrayEnum, Long>>()

        fun getPrayersForDate(date: Date): List<Pair<PrayEnum, Long>> {
            val dateComponents = DateComponents.from(date)
            val params = CalculationMethod.EGYPTIAN.parameters
            val prayerTimes = PrayerTimes(coordinates, dateComponents, params)
            return listOf(
                PrayEnum.FAJR to prayerTimes.fajr.time,
                PrayEnum.ZUHR to prayerTimes.dhuhr.time,
                PrayEnum.ASR to prayerTimes.asr.time,
                PrayEnum.MAGHRIB to prayerTimes.maghrib.time,
                PrayEnum.ISHA to prayerTimes.isha.time
            )
        }

        // نحاول نجيب صلوات انهارده اللي لسه جايه
        val todayPrayers = getPrayersForDate(Date())
        for ((prayer, time) in todayPrayers) {
            if (time > now) {
                result.add(prayer to time)
            }
        }

        // لو مفيش صلوات فاضلة انهارده نضيف صلوات بكرا كلها
        if (result.isEmpty()) {
            val tomorrow = Calendar.getInstance().apply { add(Calendar.DATE, 1) }.time
            val tomorrowPrayers = getPrayersForDate(tomorrow)
            result.addAll(tomorrowPrayers)
        }

        return result
    }




    private fun isPrayerTime(prayerEnum: PrayEnum): Boolean {
        val(prayer,time)=getNextPrayer() ?: return false
        return  prayerEnum == prayer
    }

}


