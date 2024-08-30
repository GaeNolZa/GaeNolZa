package com.example.gaenolza.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gaenolza.R
import com.example.gaenolza.ui.theme.ColorPalette
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarComponent() {
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Calendar 제목 (년도, 달)
        CalendarTitle(
            currentYearMonth = currentYearMonth,
            onYearMonthChanged = { newYearMonth ->
                currentYearMonth = newYearMonth
                // Adjust selectedDate if it's not in the new month
                if (selectedDate.year != newYearMonth.year || selectedDate.month != newYearMonth.month) {
                    selectedDate = newYearMonth.atDay(1)
                }
            }
        )

        // 요일 헤더
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 달력 그리드
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            val firstDayOfWeek = currentYearMonth.atDay(1).dayOfWeek.value % 7
            val daysInMonth = currentYearMonth.lengthOfMonth()

            // 이전 달의 날짜들
            val previousMonth = currentYearMonth.minusMonths(1)
            val daysInPreviousMonth = previousMonth.lengthOfMonth()
            repeat(firstDayOfWeek) { day ->
                val date = previousMonth.atDay(daysInPreviousMonth - firstDayOfWeek + day + 1)
                item {
                    DayCell(
                        day = date.dayOfMonth.toString(),
                        isSelected = false,
                        isCurrentDay = false,
                        isCurrentMonth = false,
                        events = getEventsForDate(date),
                        onDateSelected = {}
                    )
                }
            }

            // 현재 달의 날짜들
            repeat(daysInMonth) { day ->
                val date = currentYearMonth.atDay(day + 1)
                item {
                    DayCell(
                        day = (day + 1).toString(),
                        isSelected = date == selectedDate,
                        isCurrentDay = date == LocalDate.now(),
                        isCurrentMonth = true,
                        events = getEventsForDate(date),
                        onDateSelected = { selectedDate = date }
                    )
                }
            }

            // 다음 달의 날짜들
            val remainingCells = 42 - (firstDayOfWeek + daysInMonth)
            repeat(remainingCells) { day ->
                val date = currentYearMonth.plusMonths(1).atDay(day + 1)
                item {
                    DayCell(
                        day = (day + 1).toString(),
                        isSelected = false,
                        isCurrentDay = false,
                        isCurrentMonth = false,
                        events = getEventsForDate(date),
                        onDateSelected = {}
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 선택된 날짜의 이벤트 리스트
//        EventList(date = selectedDate)
    }
}

enum class EventType { Vacation, Seminar, BusinessTrip }

data class Event(
    val id: String,
    val leave_type: EventType,
    val comment: String,
    val timestamp: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventList(date: LocalDate) {
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

//    LaunchedEffect(date) {
//        isLoading = true
//        error = null
//        coroutineScope.launch {
//            getEventData(date) { result ->
//                result.fold(
//                    onSuccess = { fetchedEvents ->
//                        events = fetchedEvents
//                        isLoading = false
//                    },
//                    onFailure = { e ->
//                        error = e.message
//                        isLoading = false
//                    }
//                )
//            }
//        }
//    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: $error", color = Color.Red)
            }
        }

        events.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No events for this date", color = Color.Gray)
            }
        }

        else -> {
            LazyColumn {
                items(events) { event ->
                    EventCard(event = event)
                }
            }
        }
    }
}


@Composable
fun EventCard(event: Event) {
    CustomExpandCard(
        title = event.leave_type.name,
        subtitle = event.timestamp,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = when (event.leave_type) {
                            EventType.Vacation -> Color.Red
                            EventType.Seminar -> Color.Blue
                            EventType.BusinessTrip -> Color.Green
                        },
                        shape = CircleShape
                    )
            )
        },
        content = {
            Text(
                text = event.comment,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTitle(
    currentYearMonth: YearMonth,
    onYearMonthChanged: (YearMonth) -> Unit
) {
    var showYearDropdown by remember { mutableStateOf(false) }
    var showMonthDropdown by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Year selector
        Box {
            Row(
                modifier = Modifier.clickable { showYearDropdown = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentYearMonth.year}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Year")
            }
            DropdownMenu(
                expanded = showYearDropdown,
                onDismissRequest = { showYearDropdown = false }
            ) {
                (currentYearMonth.year - 5..currentYearMonth.year + 5).forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year.toString()) },
                        onClick = {
                            onYearMonthChanged(YearMonth.of(year, currentYearMonth.month))
                            showYearDropdown = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Month selector
        Box {
            Row(
                modifier = Modifier.clickable { showMonthDropdown = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentYearMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Month")
            }
            DropdownMenu(
                expanded = showMonthDropdown,
                onDismissRequest = { showMonthDropdown = false }
            ) {
                Month.entries.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)) },
                        onClick = {
                            onYearMonthChanged(YearMonth.of(currentYearMonth.year, month))
                            showMonthDropdown = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomExpandCard(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    backgroundColor: Color = Color.White,
    borderColor: Color = ColorPalette.primaryPink,
    titleTextColor: Color = Color.LightGray
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = titleTextColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = if (expanded) painterResource(id = R.drawable.icon_up)
                        else painterResource(id = R.drawable.icon_down),
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }
            }
        }
    }
}
