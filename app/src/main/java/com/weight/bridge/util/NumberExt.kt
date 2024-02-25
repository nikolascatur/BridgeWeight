package com.weight.bridge.util

import com.weight.bridge.util.Constant.NUMBER_ONLY_PATTERN
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