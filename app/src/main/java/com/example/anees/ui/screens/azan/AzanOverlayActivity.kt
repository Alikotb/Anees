package com.example.anees.ui.screens.azan

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.anees.enums.PrayEnum
import com.example.anees.ui.screens.azan.component.AzanScreen
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.extensions.setAllAlarms
import com.example.anees.utils.extensions.toArabicTime

class AzanOverlayActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }


        val prayEnum = intent.getSerializableExtra("prayEnum") as? PrayEnum
        val time = intent.getLongExtra("time" , 0)

        enableEdgeToEdge()
        setContent {
            AzanScreen(
                prayEnum = prayEnum ?: PrayEnum.FAJR,
                prayerTime = time.toArabicTime().convertNumbersToArabic(),
            ){
                setAllAlarms()
                finish()
            }
        }
    }








}