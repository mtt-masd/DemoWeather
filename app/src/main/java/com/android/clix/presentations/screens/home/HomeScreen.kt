package com.android.clix.presentations.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.clix.R
import com.android.clix.navigation.Screen
import com.android.clix.presentations.theme.corner4
import com.android.clix.presentations.theme.linearGradient
import com.android.clix.presentations.theme.mainBlack
import com.android.clix.presentations.theme.regular_14
import com.android.clix.utils.bold_16
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getWatchList()
    }

    SideEffect {

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(
                    linearGradient
                ),
                colors = topAppBarColors(
                    navigationIconContentColor = Color(0xFF747474),
                    containerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = "Weather City"
                    )
                },
                actions =
                {
                    IconButton(onClick = {
                        navController.navigate(Screen.Search.route)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "ic_search",
                            tint = colorResource(id = R.color.white),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(mainBlack)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewModel.favoriteCities.value.let { cities ->
                if (cities.isNotEmpty()) {
                    cities.forEach { city ->
                        item {
                            Box(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .border(1.dp, shape = corner4, color = Color.White)
                                        .padding(12.dp)
                                        .clickable {
                                            navController.currentBackStackEntry?.savedStateHandle?.set("data", Gson().toJson(city))
                                            navController.navigate(Screen.Detail.route)
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .size(32.dp),
                                        painter = painterResource(id = R.drawable.ic_menu_home),
                                        contentDescription = "image description",
                                        contentScale = ContentScale.Fit
                                    )

                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 8.dp),
                                        text = "${city.name} - ${city.country}",
                                        style = bold_16,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "No favorite cities",
                            style = regular_14,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        viewModel.error.value?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = it,
                style = regular_14,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

