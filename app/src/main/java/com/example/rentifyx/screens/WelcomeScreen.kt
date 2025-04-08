package com.example.rentifyx.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.rentifyx.R
import com.example.rentifyx.navigation.Routes
import com.example.rentifyx.ui.theme.RentifyXTheme
import com.example.rentifyx.ui.theme.WelcomeScreenColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController



@Preview
@Composable
private fun PreviewFunction() {
    val navController = NavController(LocalContext.current)
    WelcomeScreen(navController)
}
@Composable
fun WelcomeScreen(navController: NavController) {

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = WelcomeScreenColor,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = WelcomeScreenColor,
            darkIcons = false,
            navigationBarContrastEnforced = false
        )
    }

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
                val (title, subtitle, image, signInBtn, guestBtn) = createRefs()

                val guidelineTop = createGuidelineFromTop(0.02f)
                val topGuidelineForImg = createGuidelineFromTop(0.3f)
                val startGuideline = createGuidelineFromStart(0.08f)
                val endGuideline = createGuidelineFromEnd(0.08f)

                Text(
                    text = "Why Buy?\nJust Rent!",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(guidelineTop, margin = 30.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                )

                Text(
                    text = "Find what you need, rent from verified owners, and save money effortlessly",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(subtitle) {
                        top.linkTo(title.bottom, margin = 16.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                )

                Image(
                    painter = painterResource(R.drawable.welcome_screen_image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .constrainAs(image) {
                            top.linkTo(topGuidelineForImg)
                            start.linkTo(startGuideline)
                            end.linkTo(endGuideline)
                            width = Dimension.fillToConstraints
                        }
                )

                FilledTonalButton(
                    onClick = {
                        navController.navigate(Routes.UserDetailsScreen.route)
                    },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.White,
                        contentColor = WelcomeScreenColor
                    ),
                    modifier = Modifier
                        .height(52.dp)
                        .constrainAs(signInBtn) {
                            bottom.linkTo(guestBtn.top, margin = 12.dp)
                            start.linkTo(startGuideline)
                            end.linkTo(endGuideline)
                            width = Dimension.fillToConstraints
                        },
                    shape = RoundedCornerShape(30.dp)

                ) {
                    Text("Sign In with Google", style = MaterialTheme.typography.bodyLarge)
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate(Routes.HomeScreen.route)
                    },
                    border = BorderStroke(0.dp, Color.Transparent),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(52.dp)
                        .constrainAs(guestBtn) {
                            bottom.linkTo(parent.bottom, margin = 24.dp)
                            start.linkTo(startGuideline)
                            end.linkTo(endGuideline)
                            width = Dimension.fillToConstraints
                        }
                ) {
                    Text("Continue as Guest", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}



