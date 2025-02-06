package com.example.noteapp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.noteapp.R


val productSansFontFamily = FontFamily(
    Font(R.font.product_sans_regular) ,
    Font(R.font.product_sans_bold),
    Font(R.font.product_sans_bold_italic),
    Font(R.font.product_sans_italic)

)

// Define a TextStyle for the regular font
val regularTextStyle = TextStyle(
    fontFamily = productSansFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp

)
