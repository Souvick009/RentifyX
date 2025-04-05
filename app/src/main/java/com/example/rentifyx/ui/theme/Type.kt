package com.example.rentifyx.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rentifyx.R

val MontserratBold = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Bold)
)
val MontserratNormal = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Normal)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = MontserratBold,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.7.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MontserratBold,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.6.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratNormal,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.7.sp
    )
)