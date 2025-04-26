package com.example.anees.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.ui.navigation.ScreenRout.AzkarDetailsScreen
import com.example.anees.ui.screens.azkar.AdhkarDetailsScreen
import com.example.anees.ui.screens.azkar.AdhkarScreen
import com.example.anees.ui.screens.home.HomeScreen
import com.example.anees.ui.screens.qibla.QiblaScreen
import com.example.anees.ui.screens.quranIndex.QuranIndexScreen
import com.example.anees.ui.screens.quran.QuranPDFViewerScreen
import com.example.anees.ui.screens.sebha.SebihaScreen
import com.example.anees.ui.screens.splash.SplashScreen
import com.example.anees.utils.Constants


@Composable
fun SetUpNavHost(
    navController: NavHostController,
) {
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
                },
                navToAzkar = {
                    navController.navigate(ScreenRout.AdhkarScreen)
                },
                )
        }
        composable<ScreenRout.Sebiha> {
            SebihaScreen {
                navController.popBackStack()
                navController.navigate(ScreenRout.HomeScreen)
            }
        }
        composable<ScreenRout.QiblaScreen> {
            QiblaScreen()
        }
        composable<ScreenRout.CompleteQuranScreen> {
           val sharedPref = SharedPreferencesImpl(navController.context)
            val initPage = sharedPref.fetchData(Constants.CURRENT_PAGE_INDEX, 658)
            QuranPDFViewerScreen(
                initPage = initPage,
                onIndexButtonClick = {
                navController.navigate(ScreenRout.QuranIndexScreen)
            })
        }

        composable<ScreenRout.AdhkarScreen> {
            AdhkarScreen { cat ->
                navController.popBackStack()
                navController.navigate(AzkarDetailsScreen(cat))
            }
        }
        composable<AzkarDetailsScreen> {
            val asd = it.arguments?.getString("category") ?: ""
            AdhkarDetailsScreen(asd) {
                navController.popBackStack()
                navController.navigate(ScreenRout.AdhkarScreen)
            }
        }
        composable<ScreenRout.QuranIndexScreen> {
            QuranIndexScreen(){
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(ScreenRout.CompleteQuranScreen)
            }
        }

    }
}