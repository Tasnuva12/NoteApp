package com.example.noteapp.ui.theme
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
val dateTextStyle = TextStyle(
    fontFamily = productSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp

)
val dateNumberTextStyle = TextStyle(
    fontFamily = productSansFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp

)

