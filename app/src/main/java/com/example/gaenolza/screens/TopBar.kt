package com.example.gaenolza.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gaenolza.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSearchClick: () -> Unit,
    showSearch: Boolean
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF5BA0), Color(0xFFFFEEF5))
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    TopBarChar(text = "오", fontSize = 33.22f, lineHeight = 35.88f)
                    TopBarChar(text = "늘", fontSize = 18.98f, lineHeight = 24.3f)
                    TopBarChar(text = "도", fontSize = 23.73f, lineHeight = 26.58f)
                    Spacer(modifier = Modifier.size(10.dp))
                    TopBarChar(text = "개", fontSize = 42.71f, lineHeight = 44.85f)
                    TopBarChar(text = "놀", fontSize = 33.22f, lineHeight = 35.88f)
                    TopBarChar(text = "자", fontSize = 33.22f, lineHeight = 35.88f)
                    TopBarChar(text = "!", fontSize = 33.22f, lineHeight = 35.88f, color = Color(0xFFFF5BA0))
                }

                IconButton(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color(0xFFFF0099),
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
        }
        if (showSearch) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* TODO: Implement search functionality */ },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("검색어를 입력하세요") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
            ) {
                // 검색 제안 내용
                // TODO: Implement search suggestions
            }
        }
    }


@Composable
private fun TopBarChar(
    text: String,
    fontSize: Float,
    lineHeight: Float,
    color: Color = Color.White
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            lineHeight = lineHeight.sp,
            fontFamily = FontFamily(Font(R.font.poppins_bold)),
            fontWeight = FontWeight(700),
            color = color,
            shadow = androidx.compose.ui.graphics.Shadow(
                color = Color.Gray,
                offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                blurRadius = 4f
            )
        )
    )
}