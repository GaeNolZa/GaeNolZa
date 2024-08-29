package com.example.gaenolza.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HotelScreen() {
    val cardHeight = 200.dp
    val cardWidth = 300.dp
    val horizontalCards = remember { generateItems(2) }
    val verticalCards = remember { generateItems(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
        ) {
            item {
                Text(
                    "가로 스크롤 카드",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(horizontalCards) { item ->
                        HomeCard(
                            title = item.title,
                            content = item.content,
                            onClick = { },
                            modifier = Modifier
                                .width(cardWidth)
                                .height(cardHeight)
                        )
                    }
                }
            }
            items(verticalCards) { item ->
                Spacer(modifier = Modifier.height(16.dp))
                HomeCard(
                    title = item.title,
                    content = item.content,
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .padding(vertical = 4.dp)
                )
            }
            items(verticalCards) { item ->
                Spacer(modifier = Modifier.height(16.dp))
                HomeCard(
                    title = item.title,
                    content = item.content,
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}