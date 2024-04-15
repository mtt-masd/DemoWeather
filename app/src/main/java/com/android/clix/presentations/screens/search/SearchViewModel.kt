package com.android.clix.presentations.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.android.clix.base.BaseViewModel
import com.android.clix.model.response.CityDataResponse
import com.android.clix.utils.APP_ID
import com.android.clix.utils.UNITS
import com.android.clix.utils.Utils.retryWithBackoff
import com.example.android.codelabs.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    val searchCity: MutableState<String> = mutableStateOf("")
    val searchResult: MutableState<CityDataResponse?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val error: MutableState<String?> = mutableStateOf("")

    fun search() {
        if (searchCity.value.isEmpty())
            return
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = withContext(Dispatchers.IO) {
                try {
                    retryWithBackoff(
//                        times = 3, // Retry up to 3 times
                        delay = 5000, // Delay 5 seconds between retries
                    ) {
                        ApiService().getAPIServiceAuthorization()?.search(searchCity.value, APP_ID, UNITS)
                    }
                } catch (e: Exception) {
                    Log.e("SearchViewModel", "Error fetching data ${e.message}")
                    loading.value = false
                    return@withContext null
                }
            } as CityDataResponse?

            if (result?.cod == 200) {
                error.value = null
                searchResult.value = result
            } else {
                error.value = result?.message
                searchResult.value = null
            }
        }
    }
}