package com.artem_obrazumov.habits.common.data.converters

import androidx.room.TypeConverter
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency

class ProgressFrequencyConverter {

    private val codesMap = ProgressFrequency.entries.associateWith { it.name }
    private val frequencyMap = ProgressFrequency.entries.associateBy { it.toString() }

    @TypeConverter
    fun fromProgressFrequency(frequency: ProgressFrequency): String {
        return codesMap[frequency] ?: throw IllegalArgumentException("Unknown ProgressFrequency: ${frequency.name}")
    }

    @TypeConverter
    fun toProgressFrequency(frequencyString: String): ProgressFrequency {
        return frequencyMap[frequencyString] ?: throw IllegalArgumentException("Unknown ProgressFrequency: $frequencyString")
    }
}