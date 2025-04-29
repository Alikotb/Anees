package com.example.anees.ui.screens.hadith

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.Response
import com.example.anees.data.model.toHadith
import com.example.anees.enums.AuthorEdition
import com.example.anees.utils.hadith_helper.cardColors
import com.example.anees.utils.hadith_helper.offline_hadith.OfflineHadithHelper
import com.example.anees.utils.extensions.isInternetAvailable


@Composable
fun HadithScreen(author: AuthorEdition, id: String, viewModel: HadithViewModel = hiltViewModel()) {

    val sectionsState by viewModel.sectionsState.collectAsStateWithLifecycle()
    val ctx = LocalContext.current
    val isOnline = ctx.isInternetAvailable()

    LaunchedEffect(Unit) {
        viewModel.getSections(author)
    }

    if (!isOnline) {
        Column {


            val hadithOffline =
                OfflineHadithHelper.getAllHadith(ctx, author, id)

            val list = hadithOffline?.hadiths?.map {
                it.toHadith()
            } ?: emptyList()

            DisplayOfflineHadiths(list)

        }

    }else {
        Column(modifier = Modifier.padding(16.dp)) {
            when (val state = sectionsState) {
                is Response.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Response.Success -> {
                    val sectionDetail = getSectionDetail(state.data.metadata.sectionDetails, id)
                    if (sectionDetail != null) {
                        val hadithNumberRange =
                            sectionDetail.hadithnumberFirst to sectionDetail.hadithnumberLast

                        Column {

                                DisplayHadiths(state.data.hadiths, hadithNumberRange)

                        }
                    }
                }

                is Response.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

}

@Composable
fun DisplayHadiths(allHadiths: List<EditionResponse.Hadith>, hadithRange: Pair<Double, Double>) {
    val hadiths = getHadithsForRange(allHadiths, hadithRange.first, hadithRange.second)

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hadiths) { hadith ->
            HadithCard(hadith)
        }
    }
}

@Composable
fun DisplayOfflineHadiths(allHadiths: List<EditionResponse.Hadith>) {
    val hadiths = allHadiths.map { it.text }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hadiths) { hadith ->
            HadithCard(hadith)
        }
    }
}

@Composable
fun HadithCard(hadithText: String) {
    val backgroundColor = remember { cardColors.random() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = hadithText.replace("<br>", "\n"),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Right
                )
            )
        }
    }
}

fun getHadithsForRange(allHadiths: List<EditionResponse.Hadith>, start: Double, end: Double): List<String> {
    return allHadiths.filter { it.hadithnumber in start..end && it.text.isNotBlank() }
        .map { it.text }
}

fun getSectionDetail(sectionDetails: EditionResponse.Metadata.SectionDetails, id: String): EditionResponse.Metadata.SectionDetails.SectionDetail? {
    return when (id) {
        "0" -> sectionDetails.x0
        "1" -> sectionDetails.x1
        "2" -> sectionDetails.x2
        "3" -> sectionDetails.x3
        "4" -> sectionDetails.x4
        "5" -> sectionDetails.x5
        "6" -> sectionDetails.x6
        "7" -> sectionDetails.x7
        "8" -> sectionDetails.x8
        "9" -> sectionDetails.x9
        "10" -> sectionDetails.x10
        "11" -> sectionDetails.x11
        "12" -> sectionDetails.x12
        "13" -> sectionDetails.x13
        "14" -> sectionDetails.x14
        "15" -> sectionDetails.x15
        "16" -> sectionDetails.x16
        "17" -> sectionDetails.x17
        "18" -> sectionDetails.x18
        "19" -> sectionDetails.x19
        "20" -> sectionDetails.x20
        "21" -> sectionDetails.x21
        "22" -> sectionDetails.x22
        "23" -> sectionDetails.x23
        "24" -> sectionDetails.x24
        "25" -> sectionDetails.x25
        "26" -> sectionDetails.x26
        "27" -> sectionDetails.x27
        "28" -> sectionDetails.x28
        "29" -> sectionDetails.x29
        "30" -> sectionDetails.x30
        "31" -> sectionDetails.x31
        "32" -> sectionDetails.x32
        "33" -> sectionDetails.x33
        "34" -> sectionDetails.x34
        "35" -> sectionDetails.x35
        "36" -> sectionDetails.x36
        "37" -> sectionDetails.x37
        "38" -> sectionDetails.x38
        "39" -> sectionDetails.x39
        "40" -> sectionDetails.x40
        "41" -> sectionDetails.x41
        "42" -> sectionDetails.x42
        "43" -> sectionDetails.x43
        "44" -> sectionDetails.x44
        "45" -> sectionDetails.x45
        "46" -> sectionDetails.x46
        "47" -> sectionDetails.x47
        "48" -> sectionDetails.x48
        "49" -> sectionDetails.x49
        "50" -> sectionDetails.x50
        "51" -> sectionDetails.x51
        "52" -> sectionDetails.x52
        "53" -> sectionDetails.x53
        "54" -> sectionDetails.x54
        "55" -> sectionDetails.x55
        "56" -> sectionDetails.x56
        "57" -> sectionDetails.x57
        "58" -> sectionDetails.x58
        "59" -> sectionDetails.x59
        "60" -> sectionDetails.x60
        "61" -> sectionDetails.x61
        else -> null
    }
}