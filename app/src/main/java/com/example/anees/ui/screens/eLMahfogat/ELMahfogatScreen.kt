package com.example.anees.ui.screens.eLMahfogat

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anees.ui.screens.eLMahfogat.component.ElMahafogatTitleCard
import com.example.anees.ui.screens.eLMahfogat.component.ElMahfogatSectionLabel
import com.example.anees.ui.screens.eLMahfogat.component.EmptyPlaceholder
import com.example.anees.ui.screens.eLMahfogat.components.ElMahfogatContentList
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.hisn_almuslim_helper.HisnAlMuslimHelper
import com.example.anees.utils.azkar_helper.AzkarUtils

@Composable
fun ElMahfogatScreen(
    navToHome: () -> Unit = {},
    navToAzkarDetails: (String) -> Unit = {},
    viewModel: ElMahfogatViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val savedAzkar by viewModel.savedAzkarCategories.collectAsState()
    val savedAd3ya by viewModel.savedAd3yaTitles.collectAsState()

    // Section 1: الأذكار
    val categories = remember {
        val azkarList = AzkarUtils.parseAdhkar(context)
        AzkarUtils.getAdhkarCategories(azkarList)
            .filter { it in savedAzkar }
    }

    // Section 2: الأدعية
    val ad3yaFile = "hisn/hisn_almuslim.json"
    val ad3yaTitles = remember {
        HisnAlMuslimHelper.getTitles(context, ad3yaFile)
            .filter { it in savedAd3ya }
            .map { title ->
                val (texts, _) = HisnAlMuslimHelper.getSectionContent(context, ad3yaFile, title)
                title to texts
            }
    }
    val ad3yaExpanded = remember { mutableStateMapOf<String, Boolean>() }

    // val quranFile = "hisn/hisn_almuslim.json"
    // val quranTitles = remember {
    //     HisnAlMuslimHelper.getTitles(context, quranFile).map { title ->
    //         val (texts, _) = HisnAlMuslimHelper.getSectionContent(context, quranFile, title)
    //         title to texts
    //     }
    // }

    ScreenBackground()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ScreenTitle(
            title = "المحفوظات",
            onBackClick = { navToHome() },
            size = 28,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .padding(bottom = 8.dp)
        )
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding( 16.dp)
                .padding(top = 12.dp)
                .systemBarsPadding()
            ) {

            // Section 1
            item { ElMahfogatSectionLabel("الأذكار") }

            if (categories.isEmpty()) {
                item { EmptyPlaceholder(message = "لا يوجد أذكار محفوظة") }
            } else {
                items(categories) { category ->
                    ElMahafogatTitleCard(
                        title = category,
                        navToAzkarDetails = { navToAzkarDetails(category) }
                    )
                }
            }


            // Section 2
            item { ElMahfogatSectionLabel("الأدعية") }

            if (ad3yaTitles.isEmpty()) {
                item { EmptyPlaceholder(message = "لا يوجد أدعية محفوظة") }
            } else {
                item {
                    ElMahfogatContentList(
                        titlesWithTexts = ad3yaTitles,
                        expandedStates = ad3yaExpanded
                    )
                }
            }

            // Section 3
            item { ElMahfogatSectionLabel("القراءات") }
            // item {
            //     ElMahfogatContentList(
            //         titlesWithTexts = quranTitles,
            //         expandedStates = quranExpanded
            //     )
            // }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
