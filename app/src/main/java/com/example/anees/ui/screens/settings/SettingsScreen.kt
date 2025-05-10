package com.example.anees.ui.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.enums.AzanRecitersEnum
import com.example.anees.enums.FajrRecitersEnum
import com.example.anees.enums.ZekirIntervalsEnum
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.ui.screens.settings.Component.SettingDropdownMenu
import com.example.anees.ui.screens.settings.Component.SettingSection
import com.example.anees.ui.screens.settings.Component.SettingSwitchRow
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview(showBackground = true)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onAzanViewClick: () -> Unit = {},
    onFajarClick: () -> Unit = {},
) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }


        val viewModel : SettingsViewModel = hiltViewModel()
    val textColor = Color.Black
    val switchColor = Color(0xFF4CAF50)
    val sectionColor  = Color(0xFFFAF9F6)


    var zekrNotificationState = viewModel.zekrNotificationState.collectAsStateWithLifecycle()
    var AzanNotificationState = viewModel.azanNotificationState.collectAsStateWithLifecycle()
    var selectedFajr = viewModel.currentFajrReciter.collectAsStateWithLifecycle()
    var selectedInterval = viewModel.currentZekirInterval.collectAsStateWithLifecycle()
    var selectedAzan = viewModel.currentAzanReciter.collectAsStateWithLifecycle()


    val intervals = ZekirIntervalsEnum.values().map { it.label }
    val AzanList =AzanRecitersEnum.values().map { it.label }
    val fajrList =FajrRecitersEnum.values().map { it.label }

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ScreenTitle(title = "الاعدادات", onBackClick = onBackClick, size = 24)

            SettingSection(title = "إعدادات الأذكار" , color = sectionColor) {
                SettingSwitchRow(
                    title = "تفعيل اشعارات الأذكار",
                    isChecked = zekrNotificationState.value,
                    color = switchColor,
                    onCheckedChange = {
                        viewModel.updateZekirNotificationState(it)
                    }
                )
                SettingDropdownMenu(
                    title = "المدة بين الاشعارات",
                    options = intervals,
                    textColor = textColor,
                    selectedOption = ZekirIntervalsEnum.getLabelByValue(selectedInterval.value),
                    onOptionSelected = {
                        viewModel.updateCurrentZekirInterval(ZekirIntervalsEnum.getValueByLabel(it))
                    }
                )
            }

            SettingSection(title = "إعدادات الأذان" , color = sectionColor) {
                SettingSwitchRow(
                    title = "تفعيل تنبيهات الأذان",
                    isChecked = AzanNotificationState.value,
                    color = switchColor,
                    onCheckedChange = {
                        viewModel.updateAzanNotificationState(it)
                    }
                )
                SettingDropdownMenu(
                    title = "أذان صلاة الفجر",
                    options = fajrList,
                    selectedOption = selectedFajr.value,
                    textColor = textColor,
                    onOptionSelected = {
                        viewModel.updateCurrentFajrReciter(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = onFajarClick) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = null,
                                tint = switchColor,
                            )
                        }
                    }

                )
                SettingDropdownMenu(
                    title = "أذان باقي الصلوات",
                    options = AzanList,
                    selectedOption = selectedAzan.value,
                    textColor = textColor,
                    onOptionSelected = {
                        viewModel.updateCurrentAzanReciter(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = onAzanViewClick) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = null,
                                tint = switchColor,
                            )
                        }
                    }
                )
            }
        }
    }
}
