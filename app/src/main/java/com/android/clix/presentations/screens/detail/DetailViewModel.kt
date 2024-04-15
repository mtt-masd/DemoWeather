package com.android.clix.presentations.screens.detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.android.clix.base.BaseViewModel
import com.android.clix.data.local.City
import com.android.clix.data.local.CityDao
import com.android.clix.model.ForeCastItem
import com.android.clix.model.response.CityDataResponse
import com.android.clix.model.response.ForeCastDataResponse
import com.android.clix.utils.APP_ID
import com.android.clix.utils.UNITS
import com.android.clix.utils.Utils
import com.example.android.codelabs.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val cityDao: CityDao) : BaseViewModel() {

    val cityData: MutableState<City?> = mutableStateOf(null)
    val forecastTodayData: MutableState<ForeCastItem?> = mutableStateOf(null)
    val forecastData: MutableState<List<ForeCastItem>> = mutableStateOf(arrayListOf())
    val loading: MutableState<Boolean> = mutableStateOf(false)

    val isWatched: MutableState<Boolean> = mutableStateOf(false)

    fun getDetail() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val listResult = withContext(Dispatchers.IO) {
                try {
                    Utils.retryWithBackoff(
//                        times = 3, // Retry up to 3 times
                        delay = 5000, // Delay 5 seconds between retries
                    ) {
                        cityData.value?.let {
                            ApiService()
                                .getAPIServiceAuthorization()
                                ?.forecast(it.lat, it.lng, 40, APP_ID, UNITS)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("DetailViewModel", "Error fetching data ${e.message}")
                    loading.value = false
                    return@withContext null
                }
            } as ForeCastDataResponse?

            if (listResult?.cod?.toInt() == 200) {
                forecastTodayData.value = listResult.list[0]
                val result = ArrayList<ForeCastItem>()
                result.add(listResult.list[7])
                result.add(listResult.list[15])
                result.add(listResult.list[23])
                forecastData.value = result
                loading.value = false
            }
        }
    }

    fun isWatched() {
        cityData.value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val count = cityDao.countItemById(it.id)
                isWatched.value = count > 0
            }
        }
    }

    fun addToWatchList() {
        cityData.value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val result = cityDao.insertCity(it)
                isWatched.value = result > 0
            }
        }
    }

    fun removeToWatchList() {
        cityData.value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                cityDao.deleteCity(it)
                isWatched.value = false
            }
        }
    }
}