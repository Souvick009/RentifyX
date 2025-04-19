package com.example.rentifyx.reusablecomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.rentifyx.ui.theme.RentifyXTheme
import com.example.rentifyx.ui.theme.backgroundColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    toolbarTitleText: String = "",
    navigationIcon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null,
    isAppBarNeeded: Boolean,
    showDivider: Boolean = false,
    backgroundColorForSurface: Color? = null,
    dividerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 1f),
    bottomAppBar: @Composable () -> Unit = {},
    statusBarColor: Color = backgroundColor,
    navigationBarColor: Color = backgroundColor,
    content: @Composable (PaddingValues) -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = statusBarColor, darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor, darkIcons = true, navigationBarContrastEnforced = false
        )
    }

    RentifyXTheme {
        Scaffold(
            topBar = {
                if (isAppBarNeeded) {
                    CustomToolbar(
                        titleText = toolbarTitleText,
                        navigationIcon = navigationIcon,
                        navigationOnClick = navigationOnClick,
                        showDivider = showDivider,
                        dividerColor = dividerColor
                    )
                }
            },
            modifier = modifier
                .fillMaxSize()
                .imePadding(),
            containerColor = backgroundColorForSurface ?: MaterialTheme.colorScheme.background,
            bottomBar = bottomAppBar
        ) {
            content(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    modifier: Modifier = Modifier,
    titleText: String = "",
    navigationIcon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null,
    showDivider: Boolean = true,
    dividerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 1f)
) {
    RentifyXTheme {
        Column(modifier = modifier) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        titleText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    )
                },
                navigationIcon = {
                    navigationIcon?.let {
                        IconButton(onClick = { navigationOnClick?.invoke() }) {
                            Icon(imageVector = it, contentDescription = "")
                        }
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.background
                ),
            )
            if (showDivider) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = dividerColor
                )
            }
        }
    }
}