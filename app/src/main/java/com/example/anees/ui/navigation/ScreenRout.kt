package com.example.anees.ui.navigation

import kotlinx.serialization.Serializable


sealed class ScreenRout {
    @Serializable
    object SplashScreen : ScreenRout()
    @Serializable
    object HomeScreen : ScreenRout()
    @Serializable
    object Sebiha : ScreenRout()
    @Serializable
    object QiblaScreen : ScreenRout()
    @Serializable
    object CompleteQuranScreen : ScreenRout()

}