package com.android.clix.model.response

import com.android.clix.model.CityX
import com.android.clix.model.ForeCastItem

data class ForeCastDataResponse(
    val city: CityX,
    val cnt: Int,
    val cod: String,
    val list: List<ForeCastItem>,
    val message: Int
)