/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.clix.presentations

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.clix.ClixApplication
import com.android.clix.R
import com.android.clix.navigation.Navigation
import com.android.clix.presentations.theme.BasicTheme
import com.android.clix.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setColorStatusBarDefault()
        setContent {
            BasicTheme {
                navHostController = rememberNavController()
                Navigation(navHostController)
            }
        }
    }

    fun setColorStatusBarDefault() {
        setColorStatusBar()
        setColorNavigationBar()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun setColorStatusBar() {
        window.statusBarColor = ContextCompat.getColor(
            applicationContext, R.color.vampire_black
        )
    }

    private fun setColorNavigationBar() {
        window.navigationBarColor = ContextCompat.getColor(
            applicationContext, R.color.vampire_black
        )
    }
}