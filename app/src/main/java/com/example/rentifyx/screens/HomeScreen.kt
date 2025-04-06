package com.example.rentifyx.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.rentifyx.reusablecomposable.BaseScreen
import com.example.rentifyx.reusablecomposable.HorizontalPages
import com.example.rentifyx.ui.theme.RentifyXTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun HomeScreen() {
    var searchText by rememberSaveable { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pagerState.animateScrollToPage(0)
    }

    LaunchedEffect(pagerState.currentPage) {
        delay(2000)
        val nextPage = (pagerState.currentPage + 1) % 3
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }

    BackHandler(enabled = isFocused) {
        focusManager.clearFocus()
        isFocused = false
    }

    RentifyXTheme {
        BaseScreen(
            modifier = Modifier,
            isAppBarNeeded = true,
            toolbarTitleText = "RentifyX",
            backgroundColorForSurface = MaterialTheme.colorScheme.background,
            dividerColor = MaterialTheme.colorScheme.background
        ) { padding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                item {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        val (searchTextField, horizontalSlider, browseByCategoryText, lazyRowCategory, browseByProductText) = createRefs()

                        val verticalGuideLineStart = createGuidelineFromStart(0.00f)
                        val verticalGuideLineEnd = createGuidelineFromEnd(0.00f)

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    "Search For Products",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            leadingIcon = {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            },
                            shape = MaterialTheme.shapes.extraLarge,
                            modifier = Modifier
                                .constrainAs(searchTextField) {
                                    top.linkTo(parent.top)
                                    start.linkTo(verticalGuideLineStart)
                                    end.linkTo(verticalGuideLineEnd)
                                    width = Dimension.fillToConstraints
                                }
                                .onFocusChanged { isFocused = it.isFocused },
                            textStyle = MaterialTheme.typography.labelLarge,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.1f
                                ),
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.primary
                            ),
                            trailingIcon = {
                                if (searchText.isNotEmpty()) {
                                    IconButton(onClick = { searchText = "" }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Clear,
                                            contentDescription = "Clear Text"
                                        )
                                    }
                                }
                            },
                        )

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .constrainAs(horizontalSlider) {
                                    top.linkTo(searchTextField.bottom, margin = 15.dp)
                                    start.linkTo(verticalGuideLineStart)
                                    end.linkTo(verticalGuideLineEnd)
                                    width = Dimension.fillToConstraints
                                }
                                .height(180.dp)
                                .clip(MaterialTheme.shapes.extraLarge),
                        ) { page ->
                            when (page) {
                                0 -> HorizontalPages(Modifier, "Discover Local\n Deals")
                                1 -> HorizontalPages(Modifier, "Rent Items at\n lower Rates")
                                2 -> HorizontalPages(Modifier, "Shop Local \nSupport Local.")
                            }
                        }

                        Text(
                            "Browse by Category",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.constrainAs(browseByCategoryText) {
                                top.linkTo(horizontalSlider.bottom, margin = 15.dp)
                                start.linkTo(verticalGuideLineStart)
                                end.linkTo(verticalGuideLineEnd)
                                width = Dimension.fillToConstraints
                            })

                        LazyRow(
                            modifier = Modifier
                                .constrainAs(lazyRowCategory) {
                                    top.linkTo(browseByCategoryText.bottom, margin = 15.dp)
                                    start.linkTo(verticalGuideLineStart)
                                    end.linkTo(verticalGuideLineEnd)
                                    width = Dimension.fillToConstraints
                                }
                                .height(230.dp), content = {
                                items(25) { item ->
                                    Text("Testing ")
                                }
                            })

                        Text(
                            "Browse by Products",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.constrainAs(browseByProductText) {
                                top.linkTo(lazyRowCategory.bottom, margin = 15.dp)
                                start.linkTo(verticalGuideLineStart)
                                end.linkTo(verticalGuideLineEnd)
                                width = Dimension.fillToConstraints
                            })
                    }
                }
                items(100) { index ->
                    Text("Hi")
                }
            }
        }
    }
}

