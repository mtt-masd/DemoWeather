package com.android.clix.koin.components

import com.android.clix.koin.modules.localModule
import com.android.clix.koin.modules.viewModelModule


val applicationComponent = listOf(
    localModule,
    viewModelModule
)