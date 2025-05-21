package com.artem_obrazumov.habits.common.ui.util

import java.util.Locale

/*
* Formats double with specified amount of trailing digits
 */
fun Double.formatToString(
    digits: Int = 1
): String {
    return if (digits == 0) {
        this.toInt().toString()
    } else if (this == this.toLong().toDouble()) {
        String.format(Locale.US, "%.0f", this)
    } else {
        String.format(Locale.US, "%.${digits}f", this)
    }
}