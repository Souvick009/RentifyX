package com.example.rentifyx.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rentifyx.R

val MontserratExtraBold = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Bold)
)
val MontserratNormal = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Normal)
)
val MontserratMedium = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Medium)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = MontserratExtraBold,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.7.sp
    ),
    titleLarge = TextStyle( //big headings
        fontFamily = MontserratExtraBold,
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
    ),
    bodySmall = TextStyle(
        fontFamily = MontserratMedium,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.7.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratNormal,
        fontWeight = FontWeight.W600,
        fontSize = 15.sp,
        lineHeight = 0.sp,
        letterSpacing = 0.7.sp
    )
)