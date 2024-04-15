package com.android.clix.presentations.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.clix.R
import com.android.clix.navigation.Screen
import com.android.clix.presentations.theme.corner4
import com.android.clix.presentations.theme.linearGradient
import com.android.clix.presentations.theme.mainBlack
import com.android.clix.presentations.theme.regular_14
import com.android.clix.presentations.theme.regular_16
import com.android.clix.utils.bold_16
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val focusManager: FocusManager = LocalFocusManager.current

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
                        text = "Add a City"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_button),
                            contentDescription = "ic_search",
                            tint = colorResource(id = R.color.white),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(mainBlack)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                onValueChange = {
                    viewModel.searchCity.value = it
                },
                value = viewModel.searchCity.value,
                label = {
                    Text(
                        text = "City",
                        style = regular_14
                    )
                },
                singleLine = true,
                maxLines = 1,
                textStyle = regular_16,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = colorResource(id = R.color.white),
                    disabledBorderColor = colorResource(id = R.color.white),
                    unfocusedTextColor = colorResource(id = R.color.white),
                    focusedTextColor = colorResource(id = R.color.white),
                    unfocusedLabelColor = colorResource(id = R.color.white).copy(alpha = 0.5f),
                    focusedLabelColor = colorResource(id = R.color.white),
                    focusedBorderColor = colorResource(id = R.color.white),
                    unfocusedBorderColor = colorResource(id = R.color.white),
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.search()
                    focusManager.clearFocus()
                }),
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (viewModel.loading.value) {
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }

            Box(modifier = Modifier.padding(12.dp)) {
                viewModel.searchResult.value?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .border(1.dp, shape = corner4, color = Color.White)
                            .padding(12.dp)
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set("data", Gson().toJson(it.toCity()))
                                navController.navigate(Screen.Detail.route)
                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "${it.name} - ${it.sys.country}",
                            style = bold_16,
                            color = Color.White
                        )
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
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
