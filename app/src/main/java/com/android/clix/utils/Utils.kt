package com.android.clix.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import kotlinx.coroutines.delay
import org.joda.time.DateTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {

    fun checkFutureOrPastDate(dateString: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val currentDate = Calendar.getInstance().time

        return try {
            val date = dateFormat.parse(dateString)
            if (date.time > currentDate.time) {
                1
            } else {
                -1
            }
        } catch (e: ParseException) {
            -1
        }
    }

    fun separateDateTime(dateString: String): List<String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val date = dateFormat.parse(dateString)
        val newFormat = SimpleDateFormat("MMM dd yyyy", Locale.US)
        return newFormat.format(date).split(" ")
    }

    @SuppressLint("HardwareIds")
    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);
    }

    fun getMinimumDate(): Long {
        val now = DateTime()
        return now.minusYears(100).millis
    }

    suspend fun <T> retryWithBackoff(
        times: Int = Int.MAX_VALUE,
        delay: Long = 1000L,
        maxDelay: Long = 10000L,
        block: suspend () -> T
    ): T {
        var currentDelay = delay
        var attempt = 0
        while (attempt < times) {
            try {
                return block() // Execute the block and return the result if successful
            } catch (e: Exception) {
                attempt++ // Increase attempt count only on IOException
                delay(currentDelay)
                currentDelay = minOf(currentDelay * 2, maxDelay) // Exponential backoff
            }
        }
        throw Exception("Failed to execute block after $times retries") // Throw an exception after all retries
    }

}


