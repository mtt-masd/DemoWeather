package com.android.clix.model.response

import com.android.clix.data.local.City
import com.android.clix.model.Clouds
import com.android.clix.model.Coord
import com.android.clix.model.Main
import com.android.clix.model.Sys
import com.android.clix.model.Weather
import com.android.clix.model.Wind

data class CityDataResponse(
    val id: Int,
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
    val message: String,
) {
    fun toCity(): City {
        return City(id, name, sys.country, coord.lat, coord.lon)
    }
}
