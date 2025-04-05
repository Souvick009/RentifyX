package com.example.rentifyx.reusablecomposable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.rentifyx.ui.theme.RentifyXTheme

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    titleText: String = "",
    navigationIcon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
    isToolbarNeeded: Boolean,
    backgroundColorForSurface: Color
) {
    RentifyXTheme {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .imePadding(),
            color = backgroundColorForSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding()
            ) {
                if (isToolbarNeeded) {
                    CustomToolbar(titleText, navigationIcon, navigationOnClick)
                }
                Spacer(Modifier.height(10.dp))
                content()
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    titleText: String = "",
    navigationIcon: ImageVector? = null,
    navigationOnClick: (() -> Unit)? = null
) {
    RentifyXTheme {
        Column {
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
                        IconButton(onClick = {
                            navigationOnClick?.invoke()
                        }) {
                            Icon(
                                imageVector = it,
                                contentDescription = ""
                            )
                        }

                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.background
                ),
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }

    }

}