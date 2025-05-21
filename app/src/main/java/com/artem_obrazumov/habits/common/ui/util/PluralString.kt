package com.artem_obrazumov.habits.common.ui.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artem_obrazumov.habits.R
import kotlin.math.abs

@Composable
fun getPluralString(
    count: Int,
    pluralStringSet: PluralStringSet
): String {
    val last2Numbers = abs(count) % 100
    val lastNumber = last2Numbers % 10
    return when {
        last2Numbers in 11..19 -> stringResource(pluralStringSet.plural2)
        lastNumber == 1 -> stringResource(pluralStringSet.single)
        lastNumber in 2..4 ->  stringResource(pluralStringSet.plural1)
        else -> stringResource(pluralStringSet.plural2)
    }
}

enum class PluralStringSet(
    @StringRes val single: Int,
    @StringRes val plural1: Int,
    @StringRes val plural2: Int,
) {
    Users(R.string.user_single, R.string.user_plural1, R.string.user_plural2)
}
