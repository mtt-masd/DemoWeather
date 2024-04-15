package com.android.clix.presentations.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.clix.R
import com.android.clix.data.local.City
import com.android.clix.presentations.theme.mainBlack
import com.android.clix.presentations.theme.regular_14
import com.android.clix.presentations.theme.regular_16
import com.android.clix.presentations.theme.regular_20
import com.android.clix.utils.DateTimeHelper
import com.android.clix.utils.DateTimeHelper.FORMAT_DATETIME_1
import com.android.clix.utils.DateTimeHelper.FORMAT_DATETIME_14
import com.android.clix.utils.regular_12
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = koinViewModel()
) {
    if (navController.previousBackStackEntry?.savedStateHandle?.contains("data") == true) {
        val data = navController.previousBackStackEntry?.savedStateHandle?.get<String>("data").orEmpty()
        viewModel.cityData.value = Gson().fromJson(data, City::class.java)
    }

    LaunchedEffect(Unit) {
        viewModel.isWatched()
        viewModel.getDetail()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(mainBlack)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.popBackStack()
                    },
                painter = painterResource(id = R.drawable.ic_back_button),
                contentDescription = "image description",
                contentScale = ContentScale.Fit
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = viewModel.cityData.value?.name.orEmpty(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )

            Image(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        if (viewModel.isWatched.value)
                            viewModel.removeToWatchList()
                        else
                            viewModel.addToWatchList()
                    },
                painter = painterResource(id = if (viewModel.isWatched.value) R.drawable.ic_menu_watchlist else R.drawable.ic_watch_list),
                contentDescription = "image description",
                contentScale = ContentScale.Fit
            )
        }

        if (viewModel.loading.value) {
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }

        viewModel.forecastTodayData.value?.let { today ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = "${today.main.temp.toInt()}",
                    style = regular_20,
                    fontSize = 98.sp,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 24.dp),
                    text = "°C",
                    style = regular_20,
                    color = Color.White
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${today.main.temp_min.toInt()} / ${today.main.temp_max.toInt()}  °C",
                style = regular_16,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(42.dp))

        LazyRow(content = {
            viewModel.forecastData.value.forEach { item ->
                item {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = DateTimeHelper.convertUTCToLocalNewFormat(item.dt_txt, FORMAT_DATETIME_14, FORMAT_DATETIME_1),
                            style = regular_12,
                            color = Color.White
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "T: ${item.main.temp.toInt()} °C",
                            style = regular_14,
                            color = Color.White
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "H: ${item.main.humidity} %",
                            style = regular_14,
                            color = Color.White
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "W: ${item.wind.speed} km/h",
                            style = regular_14,
                            color = Color.White
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = item.weather[0].description,
                            style = regular_14,
                            color = Color.White
                        )
                    }
                }
            }
        })
    }
}