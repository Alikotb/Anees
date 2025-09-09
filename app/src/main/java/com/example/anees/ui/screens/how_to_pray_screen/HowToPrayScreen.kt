package com.example.anees.ui.screens.how_to_pray_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.how_to_pray_screen.component.HowToPrayCard
import com.example.anees.ui.screens.how_to_pray_screen.component.HowToPrayChips
import com.example.anees.ui.screens.radio.components.ScreenBackground

@Preview(showBackground = true)
@Composable
fun HowToPrayScreen(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val aspectRatio = screenWidth.toFloat() / screenHeight.toFloat()
    ScreenBackground()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .padding(vertical = 24.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ScreenTitle(title = "صفة الصلاة الصحيحة", onBackClick = { }, size = 24)
        }
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HowToPrayChips()
            Spacer(Modifier.height(8.dp))
            HowToPrayCard(modifier = modifier.padding(horizontal = 8.dp))
        }

    }

}