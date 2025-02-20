package com.example.noteapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteapp.R
import com.example.noteapp.components.DateCard
import com.example.noteapp.ui.theme.regularTextStyle
import com.example.noteapp.viewmodels.HomeScreenViewModel
import java.time.LocalDate


@Composable
fun HomeScreen(  navController: NavController? = null,modifier: Modifier, homeViewModel: HomeScreenViewModel = viewModel()) {

    val text by homeViewModel.searchText.observeAsState("")
    val dates by homeViewModel.dates.collectAsState(emptyList())


    //check if it's time to call updateDates function
    val currentDate = LocalDate.now()
    val lastDate = dates.lastOrNull()

    LaunchedEffect(currentDate) {
       if(lastDate==null || currentDate.isAfter(lastDate)){
           homeViewModel.updateDates()
       }
    }

    Column(modifier = modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp)) {
        Box(
            modifier = Modifier
                .height(44.dp)
                .width(305.dp)
                .clip(RoundedCornerShape(16.dp)) // Adds rounded corners
                .background(colorResource(id = R.color.light_gray))
        ) {
            Row(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.searchnormal1),
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))

                BasicTextField(
                    value = text,
                    onValueChange = { newText -> homeViewModel.setSearchText(newText) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    textStyle = regularTextStyle,
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box {
                            if (text.isEmpty()) {
                                Text(
                                    text = "Search for notes",
                                    fontSize = 14.sp,
                                    color = colorResource(id = R.color.gray) // Placeholder color
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        LazyRow(

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dates.size) { index->
                val  date =dates[index]
                DateCard(
                    dayOfWeek = date.dayOfWeek.name.take(3),
                    date = date.dayOfMonth.toString(),
                    month = date.month.name.take(3),
                    isToday = date == LocalDate.now(),
                    modifier = Modifier
                )
            }

        }
    }
}
