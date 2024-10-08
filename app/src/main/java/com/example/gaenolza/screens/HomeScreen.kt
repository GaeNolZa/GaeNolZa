import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gaenolza.R
import com.example.gaenolza.Screen
import com.example.gaenolza.screens.HotelStar
import com.example.gaenolza.viewmodel.HotelData
import com.example.gaenolza.viewmodel.HotelViewModel
import com.example.gaenolza.viewmodel.ReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hotelViewModel: HotelViewModel,
    navController: NavController,
    reservationViewModel: ReservationViewModel
) {
    val hotels = hotelViewModel.hotelDataListState.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            //아이콘 다시 누르면 검색창 사라지도록 변경

            item {
                RecommendationSection(
                    onMoreClick = { /* TODO: Handle more click */ },
                    hotels, navController,
                    reservationViewModel
                )
            }
            item { PromotionCardSection() }
            item { IconButtonGrid() }
            item { VeterinarianSection(onVeterinarianClick = { /* TODO: Handle vet click */ }) }
        }
    }
}

@Composable
fun RecommendationSection(
    onMoreClick: () -> Unit,
    hotels: List<HotelData>,
    navController: NavController,
    reservationViewModel: ReservationViewModel
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(20.dp)
                    .background(Color(0xFFFF5BA0))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "개놀자 추천추천!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onMoreClick,
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF5BA0))
            ) {
                Text(
                    "더보기 >",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(hotels.size) { index ->
                RecommendationCard(
                    hotelData = hotels[index],
                    onRecommendCardTap = {
                        reservationViewModel.updatePathHotelId(hotels[index].id)
                        navController.navigate(Screen.HotelDetail.route)
                    }
                )
            }
        }
    }
}

@Composable
fun RecommendationCard(
    hotelData: HotelData,
    onRecommendCardTap: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectTapGestures { onRecommendCardTap() }
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 이미지 영역 (실제 앱에서는 이미지를 로드해야 합니다)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White)
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = hotelData.imageResId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    hotelData.name,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    HotelStar(hotelData)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        "(${hotelData.reviewCount})",
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                }
            }
        }
    }
}

//@Composable
//fun PromotionCard(
//    id: Int,
//    title: String,
//    subTitle: String,
//    buttonText: String,
//    backgroundColor: Color,
//    modifier: Modifier = Modifier,
//    onClick: (Int) -> Unit
//) {
//    Box(
//        modifier = modifier
//            .height(120.dp)
//            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
//            .clip(RoundedCornerShape(8.dp))
//            .background(backgroundColor)
//            .clickable { onClick(id) }
//            .padding(12.dp)
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = subTitle,
//                    style = TextStyle(
//                        fontSize = 12.sp,
//                        color = Color(0xFF33383F)
//                    )
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Icon(
//                    imageVector = Icons.Default.Info,
//                    contentDescription = "Info",
//                    modifier = Modifier.size(16.dp),
//                    tint = Color(0xFF33383F)
//                )
//            }
//            Text(
//                text = title,
//                style = TextStyle(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF33383F)
//                )
//            )
//            Button(
//                onClick = { onClick(id) },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
//            ) {
//                Text(buttonText, color = Color(0xFF33383F))
//            }
//        }
//    }
//}

@Composable
fun PromotionCardSection(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PromotionCard(
            id = 0,
            title = "휴가! 25%",
            subtitle = "금방 사라져요",
            buttonText = "쿠폰받기 >",
            backgroundColor = Color(0xFFFFE8EC),
            modifier = Modifier.weight(1f),
            onClick = {

            }
        )
        PromotionCard(
            id = 1,
            title = "다른 혜택",
            subtitle = "안사라져요",
            buttonText = "쿠폰받기 >",
            backgroundColor = Color(0xFFE8F4FF),
            modifier = Modifier.weight(1f),
            onClick = { }
        )
    }
}

@Composable
fun PromotionCard(
    id: Int,
    title: String,
    subtitle: String,
    buttonText: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
    //안쓰는 변수 삭제
) {
    Box(
        modifier = modifier
            .height(120.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick(id) }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = subtitle,
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    //내 폰(갤럭시22+)에서 짤려서 fontSize 수정 36->32
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF33383F)
                )
            )
            TextButton(
                onClick = { onClick(id) },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.DarkGray),
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 2.dp)
            ) {
                Text(
                    text = buttonText,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                )
            }
        }
    }
}

@Composable
fun IconButtonGrid() {
    val icons = listOf(
        "동물병원" to R.drawable.ic_vet,
        "애견 유치원" to R.drawable.ic_dogkg,
        "펫샵" to R.drawable.ic_petshop,
        "애견카페" to R.drawable.ic_cafe
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),

        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            icons.forEach { (text, iconRes) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color(0xABB77EFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = text,
                            modifier = Modifier.size(30.dp),
                            tint = Color(0xFF7D16FF)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text,
                        style = TextStyle(fontSize = 12.sp, color = Color.Black),
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun VeterinarianSection(onVeterinarianClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "별점이 높은 수의사",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF33383F)
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        repeat(3) { index ->
            VeterinarianCard(
                id = index,
                name = "의사 이름 $index",
                description = "의사에 대한 정보 $index",
                onClick = onVeterinarianClick
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun VeterinarianCard(id: Int, name: String, description: String, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .clickable { onClick(id) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_doctor),
                contentDescription = "Doctor image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF33383F)
                    )
                )
                Text(description, style = TextStyle(fontSize = 14.sp, color = Color(0xFF33383F)))
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = "Map",
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight(),
            )
        }
    }
}
