package com.example.catgal.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.catgal.R
import com.example.catgal.UiState
import com.example.catgal.domain.model.CatModel
import com.example.catgal.ui.viewmodel.MainViewModel

@Composable
fun CatGal(viewmodel: MainViewModel = hiltViewModel()) {
    val ui by viewmodel.uiState.collectAsStateWithLifecycle()
    var focusedCatModel by remember { mutableStateOf<CatModel?>(null) }
    Box {
        Column {
            when (ui) {
                is UiState.Loading -> { // Loading UI
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        LoadingScreen()
                    }
                }
                is UiState.Success -> {
                    val catList = (ui as UiState.Success).data.collectAsLazyPagingItems()
                    when (val state = catList.loadState.refresh) {
                        is LoadState.Loading -> { // Loading UI
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                LoadingScreen()
                            }
                        }
                        is LoadState.Error -> {
                            Column {
                                ErrorScreen(message = if (state.error.message!!.contains("403")) {
                                    "You need to use a VPN to view images"
                                } else {
                                    "You seem to be offline. Please check your connection"
                                })
                            }
                        }
                        else -> {}
                    }
                    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 160.dp),
                        modifier = Modifier.padding(20.dp)) {
                        items(count = catList.itemCount) { index ->
                            catList[index]?.let {  catModel ->
                                ShowCatImage(catModel = catModel) { catM ->
                                    if (focusedCatModel == null) {
                                        focusedCatModel = catM
                                    } else {
                                        focusedCatModel = null
                                    }
                                }
                            }
                        }
                        when (val state = catList.loadState.append) {
                            is LoadState.Loading -> { // Loading UI
                                item(span = { GridItemSpan(2) }) {
                                    Column (
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Bottom,
                                    ) {
                                        LoadingScreen()
                                    }
                                }
                            }
                            is LoadState.Error -> {
                                item(span = { GridItemSpan(2) }) {
                                    Column {
                                        ErrorScreen(message = if (state.error.message!!.contains("403")) {
                                            "You need to use a VPN to view images"
                                        } else {
                                            "You seem to be offline. Please check your connection"
                                        })
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
        focusedCatModel?.let { catModel ->
            Column(modifier = Modifier.fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = catModel.url, contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable { })
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Gray)
    }
}

@Composable
fun ErrorScreen(message: String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = R.drawable.ic_offline, contentDescription = "You are offline",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .wrapContentHeight(Alignment.CenterVertically))
        HorizontalDivider(
            modifier = Modifier.height(20.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(0f)
        )
        Text(text = message,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 10.sp,
            modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun ShowCatImage(catModel: CatModel, onCatImageClicked: (CatModel) -> Unit) {
    Column(modifier = Modifier
        .width(165.dp)
        .height(165.dp)
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xff1a1a1a))
        .clickable { onCatImageClicked(catModel) }) {
        AsyncImage(model = catModel.url,
            contentDescription = "cat image",
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.size(40.dp))
    }
}

@Composable
fun DisplayCatInfo(catModel: CatModel) {
    Box {
        AsyncImage(model = catModel.url, contentDescription = "")
        Text(text = catModel.id)
    }
}