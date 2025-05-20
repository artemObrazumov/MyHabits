package com.artem_obrazumov.habits.common.ui.util

import java.util.Locale

/*
* Formats double so 1.0 becomes 1 and 1.2 stays 1.2
 */
fun Double.formatToString(
    digits: Int = 1
): String {
    return if (this == this.toLong().toDouble()) {
        String.format(Locale.US, "%.0f", this)
    } else {
        String.format(Locale.US, "%.${digits}f", this)
    }
}