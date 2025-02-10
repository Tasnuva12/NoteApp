package com.example.noteapp.screen

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteapp.R



@Composable

fun NoteScreen(navController: NavController? = null,modifier: Modifier) {
    Column(modifier = modifier.padding(start = 16.dp, top = 50.dp, end = 16.dp)){
        Row(modifier=Modifier) {
            Icon(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = "Your Image Description",
               
            )
            Spacer(modifier = Modifier.padding(80.dp))
            Icon(
                painter = painterResource(id = R.drawable.clipboardtext),
                contentDescription = "Your Image Description",

            )
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = "Your Image Description",

            )
            Spacer(modifier = Modifier.padding(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.directboxsend),
                contentDescription = "Your Image Description",

            )
        }
    }



}