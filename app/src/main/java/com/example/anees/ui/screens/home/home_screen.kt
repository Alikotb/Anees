package com.example.anees.ui.screens.home

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.ui.navigation.ScreenRoute
import com.example.anees.ui.screens.prayer.component.PrayerCardWithTimer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithDrawer(
    navToSebiha: () -> Unit = {},
    navToQibla: () -> Unit = {},
    navToQuran: () -> Unit = {},
    navToAzkar: () -> Unit = {},
    navToHadith: () -> Unit = {},
    navToRadio: () -> Unit,
    navToTafsir: () -> Unit,
    navToPrayer: () -> Unit = {},
    navToReciters: () -> Unit = {},
    navToNamesOfAllah: () -> Unit = {},
    navToHisnAlMuslim: () -> Unit = {}
) {
    val drawerState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF064789))) {
        // Right-to-Left Drawer
        CustomDrawer(
            isOpen = drawerState.value,
            onClose = { drawerState.value = false },
            onNavigate = { destination ->
                scope.launch {
                    drawerState.value = false
                    when (destination) {
                        ScreenRoute.HomeScreen -> { /* Already home */ }
                        ScreenRoute.QiblaScreen -> navToQibla()
                        ScreenRoute.AdhkarScreen -> navToAzkar()
                        ScreenRoute.NamesOfAllahScreen -> navToNamesOfAllah()
                        ScreenRoute.RadioScreen -> navToRadio()
                        ScreenRoute.TafsirScreen -> navToTafsir()
                        ScreenRoute.RecitersScreen -> navToReciters()
                        ScreenRoute.Sebiha -> navToSebiha()
                        ScreenRoute.CompleteQuranScreen -> navToQuran()
                        ScreenRoute.HadithAuthorsScreen -> navToHadith()
                        ScreenRoute.PrayerTimesScreen -> navToPrayer()
                        ScreenRoute.HisnAlMuslimScreen -> navToHisnAlMuslim()
                        else -> { /* Handle other cases or do nothing */ }
                    }
                }
            }
        )

        val currentState = if (drawerState.value) MenuState.EXPANDED else MenuState.COLLAPSED
        val transition = updateTransition(currentState, label = "menuTransition")

        val scale by transition.animateFloat(
            transitionSpec = { tween(300, easing = LinearOutSlowInEasing) },
            label = "scale"
        ) { state ->
            when (state) {
                MenuState.EXPANDED -> 0.7f
                MenuState.COLLAPSED -> 1f
            }
        }

        // Negative offset to move left when drawer opens
        val offset by transition.animateOffset(
            transitionSpec = { tween(300, easing = LinearOutSlowInEasing) },
            label = "offset"
        ) { state ->
            when (state) {
                MenuState.EXPANDED -> Offset(-450f, 0f)
                MenuState.COLLAPSED -> Offset(0f, 0f)
            }
        }

        val roundness by transition.animateDp(
            transitionSpec = { tween(300) },
            label = "roundness"
        ) { state ->
            when (state) {
                MenuState.EXPANDED -> 20.dp
                MenuState.COLLAPSED -> 0.dp
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                }
                .clip(RoundedCornerShape(roundness))
                .background(MaterialTheme.colorScheme.background)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "أنيس المسلم",
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
                actions = {
                    // Menu button on the right side
                    IconButton(onClick = { drawerState.value = !drawerState.value }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "القائمة"
                        )
                    }
                },
                navigationIcon = {}
            )

            HomeScreen(
                navToSebiha = navToSebiha,
                navToQibla = navToQibla,
                navToQuran = navToQuran,
                navToAzkar = navToAzkar,
                navToHadith = navToHadith,
                navToRadio = navToRadio,
                navToTafsir = navToTafsir,
                navToPrayer = navToPrayer,
                navToReciters = navToReciters,
                navToNamesOfAllah = navToNamesOfAllah,
                navToHisnAlMuslim = navToHisnAlMuslim
            )
        }
    }
}

@Composable
fun HomeScreen(navToSebiha: () -> Unit = {},
               navToQibla: () -> Unit = {},
               navToQuran: () -> Unit = {},
               navToAzkar: () -> Unit = {},
               navToHadith: () -> Unit = {},
               navToRadio: () -> Unit,
               navToTafsir: () -> Unit,
               navToPrayer: () -> Unit = {} ,
               navToReciters: () -> Unit = {},
               navToNamesOfAllah: () -> Unit = {},
               navToHisnAlMuslim: () -> Unit = {}
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            val city = "زفتي"
            val country = "مصر"
            PrayerCardWithTimer{
                navToPrayer()
            }
            HomeButton("Azkar") {
                navToAzkar()
            }
            HomeButton("Tasbih") {
                navToSebiha()
            }
            HomeButton("Qibla") {
                navToQibla()
            }
            HomeButton("Quran") {
                navToQuran()
            }
            HomeButton("Hadith") {
                navToHadith()
            }
            HomeButton("Radio") {
                navToRadio()
            }

            HomeButton("Tafsir") {
                navToTafsir()
            }

            HomeButton("Reciters") {
                navToReciters()
            }

            HomeButton("Names") {
                navToNamesOfAllah()
            }
            HomeButton("Hisn Al Muslim") {
                navToHisnAlMuslim()
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HomeButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

enum class MenuState {
    EXPANDED, COLLAPSED
}

@Composable
fun CustomDrawer(
    isOpen: Boolean,
    onClose: () -> Unit,
    onNavigate: (ScreenRoute) -> Unit
) {
    val drawerWidth = LocalConfiguration.current.screenWidthDp.dp
    val offsetX by animateDpAsState(
        targetValue = if (isOpen) 0.dp else drawerWidth,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth)
            .offset(x = offsetX)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "الرئيسية",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    modifier = Modifier
                        .clickable { onNavigate(ScreenRoute.HomeScreen) }
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = "القبلة",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    modifier = Modifier
                        .clickable { onNavigate(ScreenRoute.QiblaScreen) }
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = "الأذكار",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    modifier = Modifier
                        .clickable { onNavigate(ScreenRoute.AdhkarScreen) }
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = "أوقات الصلاة",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    modifier = Modifier
                        .clickable { onNavigate(ScreenRoute.PrayerTimesScreen) }
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = "أسماء الله الحسنى",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    modifier = Modifier
                        .clickable { onNavigate(ScreenRoute.NamesOfAllahScreen) }
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = "الراديو",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    modifier = Modifier
                        .clickable { onNavigate(ScreenRoute.RadioScreen) }
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
