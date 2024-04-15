package com.android.clix.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
class City(
    /**
     * Id of city
     */
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    /**
     * Name of city
     */
    @ColumnInfo(name = "name")
    val name: String = "",

    /**
     * Country of city
     */
    @ColumnInfo(name = "country")
    val country: String = "",

    /**
     * Latitude of city
     */
    @ColumnInfo(name = "lat")
    val lat: Double = Double.NEGATIVE_INFINITY,

    /**
     * Longitude of city
     */
    @ColumnInfo(name = "lng")
    val lng: Double = Double.NEGATIVE_INFINITY,
)