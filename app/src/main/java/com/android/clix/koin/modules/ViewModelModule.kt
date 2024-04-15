package com.android.clix.koin.modules


import com.android.clix.presentations.screens.detail.DetailViewModel
import com.android.clix.presentations.screens.home.HomeViewModel
import com.android.clix.presentations.screens.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        SearchViewModel()
    }

    viewModel {
        DetailViewModel(get())
    }

}