package com.example.anees.ui.screens.hisn_almuslim

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.hisn_almuslim.components.HisnAlMuslimContentList
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.hisn_almuslim_helper.HisnAlMuslimHelper

@Composable
fun HisnAlMuslimScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val fileName = "hisn/hisn_almuslim.json"

    val titlesWithTexts = remember {
        HisnAlMuslimHelper.getTitles(context, fileName).map { title ->
            val (texts, _) = HisnAlMuslimHelper.getSectionContent(context, fileName, title)
            title to texts
        }
    }

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    ScreenBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ScreenTitle(
                title = "حصن المسلم",
                onBackClick = onBackClick
            )
        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HisnAlMuslimContentList(
                titlesWithTexts = titlesWithTexts,
                expandedStates = expandedStates
            )
        }
    }
}










