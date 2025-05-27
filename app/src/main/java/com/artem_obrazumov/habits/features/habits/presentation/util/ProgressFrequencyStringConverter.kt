package com.artem_obrazumov.habits.features.habits.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artem_obrazumov.habits.R
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency

private val ProgressFrequencyToStringResMap = mapOf(
    ProgressFrequency.DAILY to R.string.frequency_daily,
    ProgressFrequency.ONCE_IN_3_DAYS to R.string.frequency_once_in_3_days,
    ProgressFrequency.WEEKLY to R.string.frequency_weekly,
    ProgressFrequency.ONCE_IN_2_WEEKS to R.string.once_in_2_weeks,
)

@Composable
fun ProgressFrequency.toStringComp(): String {
    return stringResource(ProgressFrequencyToStringResMap[this] ?: throw IllegalStateException("No such progressFrequency"))
}
