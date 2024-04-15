package com.example.catgal.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.catgal.R
import com.example.catgal.domain.model.CatModel
import com.example.catgal.ui.viewmodel.MainViewModel
import com.example.catgal.ui.viewmodel.UiState

@Composable
fun CatGal(viewmodel: MainViewModel = hiltViewModel()) {
    val ui by viewmodel.uiState.collectAsStateWithLifecycle()
    Box {
        Column {
            when (ui) {
                is UiState.Loading -> { // Loading UI
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )
                        LoadingScreen()
                    }
                }
                is UiState.Error -> {
                    Column {
                        ErrorScreen(message = (ui as UiState.Error).errorMessage ?: "Error Loading Data")
                    }
                }
                is UiState.Success -> {
                    val catList = (ui as UiState.Success).data
                    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 185.dp),
                        modifier = Modifier.padding(5.dp)) {
                        items(count = catList.size) { index ->
                            ShowCatImage(catList[index])
                        }
                    }
                }
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
        Image(painter = painterResource(id = R.drawable.ic_offline),
            contentDescription = "You are offline",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .wrapContentHeight(Alignment.CenterVertically),
            alpha = 0.3f)
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
fun ShowCatImage(catModel: CatModel) {
    Column(modifier = Modifier
        .width(185.dp)
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xff1a1a1a))) {
        AsyncImage(model = catModel.url,
            contentDescription = "cat image",
            modifier = Modifier.width(190.dp))
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
fun DisplayCatInfo(catModel: CatModel) {
    Box {
        AsyncImage(model = catModel.url, contentDescription = "")
        Text(text = catModel.id)
    }
}