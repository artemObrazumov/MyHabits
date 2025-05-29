package com.artem_obrazumov.habits.common.ui.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artem_obrazumov.habits.R
import kotlin.math.abs

fun getPluralString(
    count: Int,
    single: String,
    plural1: String,
    plural2: String
): String {
    val last2Numbers = abs(count) % 100
    val lastNumber = last2Numbers % 10
    return when {
        last2Numbers in 11..19 -> plural2
        lastNumber == 1 -> single
        lastNumber in 2..4 -> plural1
        else -> plural2
    }
}

@Composable
fun getPluralString(
    count: Int,
    pluralStringSet: PluralStringSet
): String {
    return getPluralString(
        count,
        single = stringResource(pluralStringSet.single),
        plural1 = stringResource(pluralStringSet.plural1),
        plural2 = stringResource(pluralStringSet.plural2),
    )
}

enum class PluralStringSet(
    @StringRes val single: Int,
    @StringRes val plural1: Int,
    @StringRes val plural2: Int,
) {
    Users(R.string.user_single, R.string.user_plural1, R.string.user_plural2)
}
