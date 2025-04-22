package com.example.anees.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.anees.ui.screens.home.HomeScreen
import com.example.anees.ui.screens.qibla.QiblaScreen
import com.example.anees.ui.screens.quran.CompleteQuranScreen
import com.example.anees.ui.screens.sebha.SebihaScreen
import com.example.anees.ui.screens.splash.SplashScreen

@Composable
fun SetUpNavHost(
    navController: NavHostController,
){
    NavHost(
        navController = navController,
        startDestination = ScreenRout.SplashScreen

    ) {
        composable<ScreenRout.SplashScreen> {
            SplashScreen(navToHome = {
                navController.popBackStack()
                navController.navigate(ScreenRout.HomeScreen)
            })
        }

        composable<ScreenRout.HomeScreen> {
            HomeScreen(
                navToSebiha = {
                    navController.navigate(ScreenRout.Sebiha)
                },
                navToQibla = {
                    navController.navigate(ScreenRout.QiblaScreen)
                },
                navToQuran = {
                    navController.navigate(ScreenRout.CompleteQuranScreen)
                }
            )
        }
        composable<ScreenRout.Sebiha> {
            SebihaScreen{
                navController.popBackStack()
                navController.navigate(ScreenRout.HomeScreen)
            }
        }
        composable<ScreenRout.QiblaScreen> {
            QiblaScreen()
        }
        composable<ScreenRout.CompleteQuranScreen> {
            CompleteQuranScreen()
        }

    }
}