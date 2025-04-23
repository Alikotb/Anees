package com.example.anees.ui.screens.sebha.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anees.data.model.Zekir
import  com.example.anees.utils.azkarList


@Composable
fun ZekrHorizontalStaggeredGrid(
    currentIndex: Int,
    onZekrClick: (Int) -> Unit
) {
    val rows = 3
    val chunkedLists = List(rows) { mutableListOf<Pair<Zekir, Int>>() }
    azkarList.forEachIndexed { index, zekr ->
        chunkedLists[index % rows].add(zekr to index)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        chunkedLists.forEach { zekrList ->
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items (zekrList) { (zekr, globalIndex) ->
                    ZekirCard(
                        zero = zekr,
                        onClick = {
                            onZekrClick(globalIndex)
                        },
                        index = globalIndex,
                        currentIndex = currentIndex
                    )
                }
            }
        }
    }
}