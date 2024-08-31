package com.example.gaenolza.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gaenolza.R
import com.example.gaenolza.Screen
import com.example.gaenolza.network.sendGetFacilities
import com.example.gaenolza.ui.theme.ColorPalette
import com.example.gaenolza.viewmodel.Facility
import com.example.gaenolza.viewmodel.ReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSearchClick: () -> Unit,
    showSearch: Boolean,
    navController: NavController,
    reservationViewModel: ReservationViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchResults by remember { mutableStateOf<List<Facility>>(emptyList()) }

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
                TopBarChar(
                    text = "!",
                    fontSize = 33.22f,
                    lineHeight = 35.88f,
                    color = Color(0xFFFF5BA0)
                )
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
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    // 사용자가 입력할 때마다 검색 실행
                    if (query.isNotEmpty()) {
                        sendGetFacilities(query) { result ->
                            result.fold(
                                onSuccess = { facilities ->
                                    searchResults = facilities
                                },
                                onFailure = {
                                    searchResults = emptyList() // 검색 실패 시 빈 리스트 반환
                                }
                            )
                        }
                    } else {
                        searchResults = emptyList() // 입력이 없으면 검색 결과 초기화
                    }
                },
                onSearch = { /* TODO: Implement search functionality */ },
                active = isSearchActive,
                onActiveChange = { isSearchActive = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("검색어를 입력하세요") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = { // SearchBar의 오른쪽 끝에 "닫기" 버튼 추가
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "Close Search",
                            tint = ColorPalette.primaryPink,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    items(searchResults) { facility ->
                        Text(
                            text = facility.facilityName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color.LightGray)
                                .padding(8.dp)
                                .clickable {
                                    reservationViewModel.updateHotelPath(facility.facilityId)
                                    navController.navigate(Screen.HotelDetail.route)
                                    onSearchClick()
                                }
                        )
                    }
                }
            }
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