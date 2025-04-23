package com.example.anees.ui.screens.azkar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.ui.screens.azkar.component.ZekrCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdhkarScreen(navToDetails: (String) -> Unit = {}) {
    val context = LocalContext.current
    val azkarList = remember { AzkarUtils.parseAdhkar(context) }
    val categories = remember { AzkarUtils.getAdhkarCategories(azkarList) }
    val selectedCategory = remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    Log.d("TAG", "AdhkarScreen: ${categories}")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(16.dp)
    ) {
        Text(
            text = "الأذكار اليومية",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3B3B3B),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(top = 48.dp, bottom = 16.dp, end = 12.dp)
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = "ابحث عن الذكر...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "بحث",
                    tint = Color(0xFF3B3B3B)
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF3B3B3B),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF3B3B3B)
            ),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            val filteredCategories = categories.filter {
                it.contains(searchQuery, ignoreCase = true)
            }
            items(filteredCategories) { category ->
                ZekrCard(
                    text = category,
                    isSelected = selectedCategory.value == category,
                    onClick = {
                        selectedCategory.value = category
                        navToDetails(category)
                    }
                )
            }
            item {
                if (filteredCategories.isEmpty()) {
                    Text(
                        text = "لا توجد نتائج",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3B3B3B),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                    )

                }
            }
        }
    }

}
