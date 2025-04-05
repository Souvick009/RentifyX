package com.example.rentifyx.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.rentifyx.R
import com.example.rentifyx.ui.theme.RentifyXTheme
import com.example.rentifyx.ui.theme.WelcomeScreenColor

@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenWithConstraint() {
    RentifyXTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = WelcomeScreenColor
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                val (title,
                    subtitle,
                    image,
                    signInBtn,
                    guestBtn)
                        = createRefs()
                val guidelineTop = createGuidelineFromTop(0.02f)
                Text(
                    text = "Why Buy?\nJust Rent!",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(guidelineTop, margin = 0.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                Text(
                    text = "Find what you need, rent from verified owners, and save money effortlessly",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                        .constrainAs(subtitle) {
                            top.linkTo(title.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Image(
                    painter = painterResource(R.drawable.welcome_screen_image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(1f)
                        .constrainAs(image) {
                            top.linkTo(subtitle.bottom, margin = 50.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                FilledTonalButton(
                    onClick = { },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.White,
                        contentColor = WelcomeScreenColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 24.dp)
                        .constrainAs(signInBtn) {
                            bottom.linkTo(guestBtn.top, margin = 12.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text("Sign In", style = MaterialTheme.typography.bodyLarge)
                }

                OutlinedButton(
                    onClick = { },
                    border = BorderStroke(0.dp, Color.Transparent),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 24.dp)
                        .constrainAs(guestBtn) {
                            bottom.linkTo(parent.bottom, margin = 24.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text("Continue as Guest", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}


