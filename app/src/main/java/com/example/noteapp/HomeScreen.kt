package com.example.noteapp

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier){

    var text =remember { mutableStateOf("Search for Notes") }
    Column(modifier=modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp)) {
        Box(
            modifier = Modifier
                .height(44.dp)
                .width(305.dp)
                .clip(RoundedCornerShape(16.dp)) // Adds rounded corners
                .background(colorResource(id = R.color.light_gray)),

        ) {
            Row(modifier=Modifier.padding(start=16.dp,top=10.dp)){
                Image(
                    painter = painterResource(id = R.drawable.searchnormal1),
                    contentDescription = "Search Icon",
                    modifier= Modifier.width(20.dp).height(20.dp)

                )
                Spacer(modifier=Modifier.padding(5.dp))
                BasicTextField(
                    value = text.value,
                    


                    onValueChange = { text.value = it },
                    modifier=Modifier.padding(3.dp)
                )

            }
        }
    }
}