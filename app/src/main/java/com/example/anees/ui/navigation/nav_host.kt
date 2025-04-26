package com.example.anees.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.anees.ui.navigation.ScreenRoute.AzkarDetailsScreen
import com.example.anees.ui.screens.azkar.AdhkarDetailsScreen
import com.example.anees.ui.screens.azkar.AdhkarScreen
import com.example.anees.ui.screens.hadith.HadithAuthorsScreen
import com.example.anees.ui.screens.hadith.HadithScreen
import com.example.anees.ui.screens.hadith.HadithSectionsScreen
import com.example.anees.ui.screens.home.HomeScreen
import com.example.anees.ui.screens.qibla.QiblaScreen
import com.example.anees.ui.screens.quran.QuranPDFViewerScreen
import com.example.anees.ui.screens.sebha.SebihaScreen
import com.example.anees.ui.screens.splash.SplashScreen
import com.example.anees.utils.AuthorEdition
import com.google.gson.Gson

@Composable
fun SetUpNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SplashScreen

    ) {
        composable<ScreenRoute.SplashScreen> {
            SplashScreen(navToHome = {
                navController.popBackStack()
                navController.navigate(ScreenRoute.HomeScreen)
            })
        }

        composable<ScreenRoute.HomeScreen> {
            HomeScreen(
                navToSebiha = {
                    navController.navigate(ScreenRoute.Sebiha)
                },
                navToQibla = {
                    navController.navigate(ScreenRoute.QiblaScreen)
                },
                navToQuran = {
                    navController.navigate(ScreenRoute.CompleteQuranScreen)
                },
                navToAzkar = {
                    navController.navigate(ScreenRoute.AdhkarScreen)
                },
                navToHadith = {
                    navController.navigate(ScreenRoute.HadithAuthorsScreen)
                }
            )
        }
        composable<ScreenRoute.Sebiha> {
            SebihaScreen {
                navController.popBackStack()
                navController.navigate(ScreenRoute.HomeScreen)
            }
        }
        composable<ScreenRoute.QiblaScreen> {
            QiblaScreen()
        }

        composable<ScreenRoute.CompleteQuranScreen> {
            QuranPDFViewerScreen()
        }

        composable<ScreenRoute.AdhkarScreen> {
            AdhkarScreen { cat ->
                navController.popBackStack()
                navController.navigate(AzkarDetailsScreen(cat))
            }
        }
        composable<AzkarDetailsScreen> {
            val asd = it.arguments?.getString("category") ?: ""
            AdhkarDetailsScreen(asd) {
                navController.popBackStack()
                navController.navigate(ScreenRoute.AdhkarScreen)
            }
        }
        composable<ScreenRoute.HadithScreen> {
            val author = Gson().fromJson(
                it.arguments?.getString("author"),
                AuthorEdition::class.java
            )
            val id = it.arguments?.getString("number") ?: ""
            HadithScreen(author, id)
        }
        composable<ScreenRoute.HadithAuthorsScreen> {
            HadithAuthorsScreen() { author ->
                navController.popBackStack()
                navController.navigate(ScreenRoute.HadithSectionsScreen(author))
            }
        }
        composable<ScreenRoute.HadithSectionsScreen> {
            val author = Gson().fromJson(
                it.arguments?.getString("author"),
                AuthorEdition::class.java
            )
            HadithSectionsScreen(author) { auth, id ->
                navController.popBackStack()
                navController.navigate(ScreenRoute.HadithScreen(auth, id))
            }
        }

    }
}