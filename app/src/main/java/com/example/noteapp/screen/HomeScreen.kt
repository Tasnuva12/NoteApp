package com.example.noteapp.screen

import NoteStaggerGrid
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.OneTimeWorkRequestBuilder
import com.example.noteapp.R
import com.example.noteapp.Screen
import com.example.noteapp.components.DateCard

import com.example.noteapp.ui.theme.regularTextStyle
import com.example.noteapp.viewmodels.HomeScreenViewModel
import com.example.noteapp.viewmodels.NoteScreenViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(  navController: NavController? = null,
                 modifier: Modifier, homeViewModel: HomeScreenViewModel = hiltViewModel(),noteViewModel: NoteScreenViewModel= hiltViewModel()
) {

    val text by homeViewModel.searchText.observeAsState("")
    //val dates by homeViewModel.dates.collectAsState(emptyList())
    val dates by homeViewModel.dates.collectAsState()
    val currentDate by homeViewModel.currentDate.collectAsState()









    // Check if the last date has passed and update dates if necessary



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp)
        ) {
            // Search bar and date row
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .width(305.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.light_gray))
            ) {
                Row(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.searchnormal1),
                        contentDescription = "Search Icon",
                        modifier = Modifier.size(20.dp)
                    )
                     Spacer(modifier = Modifier.width(10.dp))
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
                                        color = colorResource(id = R.color.gray)
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(dates.size) { index ->
                    val date = dates[index]
                    DateCard(
                        dayOfWeek = date.dayOfWeek.name.take(3),
                        date = date.dayOfMonth.toString(),
                        month = date.month.name.take(3),
                        isToday = date == currentDate,
                        modifier = Modifier
                    )
                }
            }

            // Scrollable Grid of Notes
            NoteStaggerGrid(
                noteViewModel = noteViewModel,
                modifier =Modifier.weight(1f) ,
                navController =navController
                // Takes remaining space but doesn't push the button

            )
        }

        // Fixed Floating Button
//        Box(
//            modifier = Modifier
//                .size(50.dp)
//                .clip(CircleShape)
//
//                .background(colorResource(R.color.black))
//
//                .align(Alignment.BottomEnd)
//
//                .clickable {
//                    navController?.navigate(Screen.NoteScreen.route)
//                }
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.add),
//                contentDescription = "Add Button",
//                tint = Color.White,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }

        Box(
            modifier = Modifier.fillMaxSize() // Takes full screen space
        ) {
            FloatingActionButton(
                onClick = {  navController?.navigate("${Screen.NoteScreen.route}/123/Test Title/Test Description") },
                containerColor = Color.Black, // Black background
                shape = CircleShape, // Round shape
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Align to bottom right
                    .padding(end=16.dp,bottom=50.dp) // Adds gap from edges
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White // White icon color
                )
            }
        }

    }

}
