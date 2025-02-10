package com.example.noteapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.ui.theme.dateNumberTextStyle
import com.example.noteapp.ui.theme.dateTextStyle


@Composable
fun DateCard(modifier: Modifier,dayOfWeek:String,date:String,month:String,isToday: Boolean){

    Box(
        modifier = Modifier
            .height(92.dp)
            .width(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .background( if(isToday)  colorResource(R.color.black) else colorResource(R.color.white))
            .border(
                width = 2.dp,
                color = if(isToday)  colorResource(R.color.black) else colorResource(R.color.light_gray),
                shape = RoundedCornerShape(20.dp)

            )
    )
    {

        Column(modifier=Modifier.align(Alignment.Center)){
            Text(text=dayOfWeek, style=if (isToday) dateTextStyle.copy(color = colorResource(id = R.color.white)) else dateTextStyle,modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding( 2.dp))
            Text(text=date,style= if (isToday) dateNumberTextStyle.copy(color = colorResource(id = R.color.white)) else dateNumberTextStyle,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text=month, style=if (isToday) dateTextStyle.copy(color = colorResource(id = R.color.white)) else dateTextStyle,modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }

}