package com.example.anees.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.anees.ui.navigation.ScreenRoute.AzkarDetailsScreen
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.ui.screens.azkar.AdhkarDetailsScreen
import com.example.anees.ui.screens.azkar.AdhkarScreen
import com.example.anees.ui.screens.hadith.HadithAuthorsScreen
import com.example.anees.ui.screens.hadith.HadithScreen
import com.example.anees.ui.screens.hadith.HadithSectionsScreen
import com.example.anees.ui.screens.home.HomeScreen
import com.example.anees.ui.screens.quran_pdf.juz_index.JuzIndexScreen
import com.example.anees.ui.screens.quran_pdf.khatm.KhatmQuranDuaScreen
import com.example.anees.ui.screens.qibla.QiblaScreen
import com.example.anees.ui.screens.quran_pdf.quran_index.QuranIndexScreen
import com.example.anees.ui.screens.quran_pdf.quran.QuranPDFViewerScreen
import com.example.anees.ui.screens.sebha.SebihaScreen
import com.example.anees.ui.screens.splash.SplashScreen
import com.example.anees.enums.AuthorEdition
import com.example.anees.ui.screens.radio.RadioScreen
import com.example.anees.enums.QuranSurah
import com.example.anees.ui.screens.tafsir.screens.TafsirDetailsScreen
import com.example.anees.ui.screens.tafsir.screens.TafsirScreen
import com.google.gson.Gson
import com.example.anees.utils.Constants


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
                },
                navToRadio = {
                    navController.navigate(ScreenRoute.RadioScreen)
                },
                navToTafsir = {
                    navController.navigate(ScreenRoute.TafsirScreen)
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
           val sharedPref = SharedPreferencesImpl(navController.context)
            val initPage = sharedPref.fetchData(Constants.CURRENT_PAGE_INDEX, 658)
            QuranPDFViewerScreen(
                initPage = initPage,
                onIndexButtonClick = {
                navController.navigate(ScreenRoute.QuranIndexScreen) },
                onKhatmButtonClick = {navController.navigate(ScreenRoute.KhatmQuranDuaScreen)
                },
                onJuzButtonClick = {navController.navigate(ScreenRoute.JuzIndexScreen)
                }
            )
        }

        composable<ScreenRoute.AdhkarScreen> {
            AdhkarScreen { cat ->
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
                navController.navigate(ScreenRoute.HadithSectionsScreen(author))
            }
        }
        composable<ScreenRoute.HadithSectionsScreen> {
            val author = Gson().fromJson(
                it.arguments?.getString("author"),
                AuthorEdition::class.java
            )
            HadithSectionsScreen(author) { auth, id ->
                navController.navigate(ScreenRoute.HadithScreen(auth, id))
            }
        }
        composable<ScreenRoute.QuranIndexScreen> {
            QuranIndexScreen(){
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(ScreenRoute.CompleteQuranScreen)
            }
        }


        composable<ScreenRoute.JuzIndexScreen> {
            JuzIndexScreen{
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(ScreenRoute.CompleteQuranScreen)
            }
        }

        composable<ScreenRoute.KhatmQuranDuaScreen> {
            KhatmQuranDuaScreen()
        }

        composable<ScreenRoute.RadioScreen> {
            RadioScreen()
        }

        composable<ScreenRoute.TafsirScreen> {
           TafsirScreen{
               navController.navigate(ScreenRoute.TafsirDetailsScreen(it))
           }
        }
        composable<ScreenRoute.TafsirDetailsScreen> {
            val surah = Gson().fromJson(
                it.arguments?.getString("surah"),
                QuranSurah::class.java
            )
            TafsirDetailsScreen(surah)
        }
    }
}