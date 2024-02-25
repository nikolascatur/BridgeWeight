package com.weight.bridge.util

import com.weight.bridge.util.Constant.NUMBER_ONLY_PATTERN
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

fun Double.toNormalizeString(): String {
    val tmp = this.toInt().toDouble()
    val nextTmp = (tmp + 1)
    return if (this > tmp && this < nextTmp) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}

fun String.toNormalizeDouble(): Double {
    return if (this.isNumberOnly()) {
        this.toDouble()
    } else {
        0.0
    }
}

fun String.isNumberOnly(): Boolean {
    return Pattern.compile(NUMBER_ONLY_PATTERN).matcher(this).matches()
}

fun Long?.convertDate(
    outputFormat: String = "dd MMMM yyyy",
    localeId: Locale = Locale("id", "ID")
): String {
    if (this != null) {
        try {
            val requiredFormat = SimpleDateFormat(outputFormat, localeId)
            return requiredFormat.format(Date(this))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return "-"
}

fun Long?.orZero(): Long = this?.let {
    this
} ?: 0L
