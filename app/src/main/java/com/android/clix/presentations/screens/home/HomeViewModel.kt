package com.android.clix.presentations.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.android.clix.base.BaseViewModel
import com.android.clix.data.local.City
import com.android.clix.data.local.CityDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val cityDao: CityDao) : BaseViewModel() {

    val favoriteCities: MutableState<List<City>> = mutableStateOf(arrayListOf())
    val error: MutableState<String?> = mutableStateOf("")

    fun getWatchList() {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteCities.value = cityDao.getAllCities()
        }
    }
}