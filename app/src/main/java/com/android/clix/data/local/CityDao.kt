package com.android.clix.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update

@Dao
interface CityDao {
    @Insert(onConflict = IGNORE)
    abstract fun insertCity(city: City): Long

    @Query("SELECT * FROM city")
    suspend fun getAllCities(): List<City>

    @Query("SELECT COUNT(*) FROM city WHERE id = :itemId")
    suspend fun countItemById(itemId: Int): Int

    @Update
    abstract fun updateCity(city: City)

    @Delete
    abstract fun deleteCity(city: City)
}