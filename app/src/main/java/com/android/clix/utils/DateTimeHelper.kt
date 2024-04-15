package com.android.clix.utils

import android.text.TextUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object DateTimeHelper {

    const val FORMAT_DATETIME_1 = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_DATETIME_7 = "MM/dd/yyyy"

    private const val EMPTY = ""
    const val FORMAT_DATETIME_12 = "yyyy-MM-dd'T'HH:mm:ss"
    const val FORMAT_DATETIME_11 = "EEEE, MMMM dd yyyy"
    const val FORMAT_DATETIME_13 = "MMM dd, yyyy"
    const val FORMAT_DATETIME_14 = "MM/dd"
    const val FORMAT_DATETIME_15 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val FORMAT_DATETIME_17 = "MM/dd/yyyy - hh:mm a"
    const val FORMAT_DATETIME_18 = "hh:mm:ss a"
    const val FORMAT_DATETIME_19 = "MMM dd, yyyy hh:mm a"
    const val FORMAT_DATETIME_20 = "MMM dd, yyyy HH:mm:ss"
    const val FORMAT_DATETIME_21 = "MM/dd/yy - HH:mm:ss a"
    const val FORMAT_DATETIME_22 = "yyyy-MM-dd__HH-mm-ss-SSS"
    const val FORMAT_DATETIME_25 = "MM/dd/yyyy - hh:mm:ss a"
    const val FORMAT_DATETIME_26 = "HH"
    const val FORMAT_DATETIME_27 = "mm"
    const val FORMAT_DATETIME_28 = "HH:mm"
    const val FORMAT_DATETIME_29 = "EEE, MMM dd"
    const val FORMAT_DATETIME_30 = "yyyy-MM-dd HH:mm"
    const val FORMAT_DATETIME_31 = "yyyy-MM-dd"
    const val FORMAT_DATETIME_32 = "MMMM yyyy"
    const val FORMAT_DATETIME_33 = "EEE, dd MMM"
    const val FORMAT_DATETIME_34 = "EEE, MMM dd"
    const val FORMAT_DATETIME_35 = "MMM. dd, yyyy"
    const val FORMAT_DATETIME_36 = "hh:mma"
    const val FORMAT_DATETIME_37 = "MMMM, yyyy"
    const val FORMAT_DATETIME_38 = "MMMM dd, hh:mm a"
    const val FORMAT_DATETIME_39 = "MMMM, YYYY"
    const val FORMAT_DATETIME_7_SHORT = "MM/dd/yy"
    const val FORMAT_DATETIME_PARTY = "MM/dd/yyyy hh:mm a"
    const val FORMAT_DATETIME_40 = "EEE MM/dd"
    const val FORMAT_DATETIME_41 = "MM/dd/yyyy HH:mm"
    const val FORMAT_DATETIME_PARTY2 = "MM/dd/yyyy  hh:mm a"

    fun validate(dateString: String?, format: String?): Boolean {
        return try {
            val sdf = SimpleDateFormat(format, Locale.US)
            val date = sdf.parse(dateString)
            dateString.equals(sdf.format(date))
        } catch (e: Exception) {
            false
        }
    }

    fun simpleDateToString(date: Date, format: String? = FORMAT_DATETIME_1): String {
        return try {
            SimpleDateFormat(format, Locale.US).format(date)
        } catch (e: Exception) {
            EMPTY
        }
    }

    fun simpleStringToDate(dateString: String?, format: String? = FORMAT_DATETIME_1): Date {
        return try {
            SimpleDateFormat(format, Locale.US).parse(dateString)
        } catch (e: Exception) {
            Date()
        }
    }

    fun simpleDateToString(time: Long, format: String? = FORMAT_DATETIME_15): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            SimpleDateFormat(format, Locale.US).format(calendar.time)
        } catch (e: Exception) {
            EMPTY
        }
    }

    fun convertUTCToLocalDate(dateString: String?, format: String? = FORMAT_DATETIME_12): Date {
        return try {
            SimpleDateFormat(format, Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.parse(dateString)
        } catch (e: Exception) {
            Date()
        }
    }

    fun convertUTCToLocalString2(
        dateString: String,
        format: String?
    ): String {
        return simpleDateToString(convertUTCToLocalDate(dateString, format), FORMAT_DATETIME_7)
    }

    fun convertUTCToLocalMillis(dateString: String?): Long {
        return convertUTCToLocalDate(dateString).time
    }

    private fun convertLocalToUTCDate(date: Date): Date {
        return try {
            val s: DateFormat = SimpleDateFormat(FORMAT_DATETIME_15, Locale.US)
            s.timeZone = TimeZone.getTimeZone("UTC")
            val dateUTC = simpleStringToDate(s.format(date), FORMAT_DATETIME_15)
            val calendar = Calendar.getInstance()
            calendar.time = dateUTC
            calendar.time
        } catch (e: Exception) {
            Date()
        }
    }

    fun convertLocalToUTCDate(time: Long): Long {
        return try {
            /* Calendar.getInstance().apply {
                    return Date(time - TimeZone.getDefault().rawOffset).time
                }*/
            val s: DateFormat = SimpleDateFormat(FORMAT_DATETIME_15, Locale.US)
            s.timeZone = TimeZone.getTimeZone("UTC")
            val dateUTC = simpleStringToDate(s.format(Date(time)), FORMAT_DATETIME_15)
            val calendar = Calendar.getInstance()
            calendar.time = dateUTC
            calendar.time.time
        } catch (e: Exception) {
            Date().time
        }
    }

    fun convertLocalToDate(time: Long): Long {
        return try {
            val s: DateFormat = SimpleDateFormat(FORMAT_DATETIME_15, Locale.US)
            val dateUTC = simpleStringToDate(s.format(Date(time)), FORMAT_DATETIME_15)
            val calendar = Calendar.getInstance()
            calendar.time = dateUTC
            calendar.time.time
        } catch (e: Exception) {
            Date().time
        }
    }

    fun convertLocalToUTCTime(dateString: String?, format: String? = FORMAT_DATETIME_12): Long {
        return convertLocalToUTCDate(simpleStringToDate(dateString, format)).time
    }

    fun convertStringToLocalTimestamp(
        dateString: String?,
        format: String? = FORMAT_DATETIME_12
    ): Long {
        return convertUTCToLocalDate(dateString, format).time
    }

    fun convertLocalToUTCString(date: Date, format: String? = FORMAT_DATETIME_12): String {
        val date_ = convertLocalToUTCDate(date)
        return simpleDateToString(date_, format)
    }

    fun convertTimeToString(date: Date, format: String? = FORMAT_DATETIME_12): String {
        val sdf = SimpleDateFormat(format, Locale.US)
        return sdf.format(date)
    }

    fun convertUTCToLocalNewFormat(dateString: String?, newFormat: String?): String {
        return simpleDateToString(convertUTCToLocalDate(dateString), newFormat)
    }


    fun Date.plus(numberDates: Int): Date {
        val it = this
        val calendar = Calendar.getInstance().apply {
            time = it
            add(Calendar.DATE, numberDates)
        }
        return calendar.time
    }

    fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
        val now = Date()
        calendar.time = now
        return calendar.time
    }

    fun getCurrentDateTime(): Long {
        val calendar = Calendar.getInstance()
        val now = Date()
        calendar.time = now
        return calendar.time.time
    }

    fun calculateAge(birthdate: Date?): Int {
        val birth = Calendar.getInstance()
        birth.time = birthdate
        val today = Calendar.getInstance()
        var yearDifference = (today[Calendar.YEAR]
                - birth[Calendar.YEAR])
        if (today[Calendar.MONTH] < birth[Calendar.MONTH]) {
            yearDifference--
        } else {
            if (today[Calendar.MONTH] === birth[Calendar.MONTH]
                && today[Calendar.DAY_OF_MONTH] < birth[Calendar.DAY_OF_MONTH]
            ) {
                yearDifference--
            }
        }
        return yearDifference
    }

    fun getMinutesOffset(): Int {
        val tz = TimeZone.getDefault()
        val calendar = Calendar.getInstance()
        val now = Date()
        calendar.time = now
        return tz.getOffset(calendar.time.time) / 1000 / 60
    }

    fun formatSecondsToString(timeInSeconds: Int): String {
        val hours = timeInSeconds / 3600
        val secondsLeft = timeInSeconds - hours * 3600
        val minutes = secondsLeft / 60
        val seconds = secondsLeft - minutes * 60
        var formattedTime = ""
        if (hours in 1..9) {
            formattedTime += if (hours == 1) {
                "$hours hour "
            } else {
                "$hours hours "
            }
        } else {
            if (hours > 0) {
                formattedTime += "$hours hours "
            }
        }

        formattedTime += if (minutes == 0 && hours > 0) {
            "0 min "
        } else if (minutes in 1..9) {
            if (minutes == 1) {
                "$minutes min "
            } else {
                "$minutes mins "
            }
        } else {
            "$minutes mins "
        }

        formattedTime += if (seconds == 0 && (hours > 0 || minutes > 0)) {
            "0 second"
        } else if (seconds in 1..9) {
            if (seconds == 1) {
                "$seconds second"
            } else {
                "$seconds seconds"
            }
        } else {
            "$seconds seconds"
        }
        return formattedTime
    }

    fun convertTimeStringToLong(
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        second: Int
    ): Long {
        if (year == 0 && month == 0 && day == 0)
            return 0
        val date = Calendar.getInstance()
        date.set(year, month, day, hour, minute, second)
        return date.timeInMillis
    }

    fun convertStringToStringCareHistory(
        dateString: String?,
        format: String? = FORMAT_DATETIME_17
    ): String {
        return try {
            simpleDateToString(simpleStringToDate(dateString, FORMAT_DATETIME_12), format)
        } catch (e: Exception) {
            dateString
        }.toString()
    }

    fun convertUTCToLocalNewFormat(
        dateString: String?,
        newFormat: String?,
        currentFormat: String?
    ): String {
        return simpleDateToString(convertUTCToLocalDate(dateString, currentFormat), newFormat)
    }


    fun convertTo12HourFormat(time24: String?): String {
        if (!TextUtils.isEmpty(time24)) {
            try {
                val parts = time24.orEmpty().split(":")
                val hour = parts[0].toInt()
                val minute = parts[1]

                val period = if (hour >= 12) "PM" else "AM"
                var hour12 = if (hour > 12) hour - 12 else hour

                if (hour == 0 && minute == "00") {
                    hour12 = 12
                }

                return String.format("%02d:%s %s", hour12, minute, period)
            } catch (e: java.lang.Exception) {
                return time24.orEmpty()
            }

        } else {
            return ""
        }
    }
}