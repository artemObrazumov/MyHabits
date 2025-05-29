package com.artem_obrazumov.habits.features.habits.data.local.converters

import com.artem_obrazumov.habits.common.data.converters.ProgressFrequencyConverter
import com.artem_obrazumov.habits.features.habits.domain.model.ProgressFrequency
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressFrequencyConverterTest {

    private val converter = ProgressFrequencyConverter()

    @Test
    fun `convert DAILY enum to string`() {
        assertEquals("DAILY", converter.fromProgressFrequency(ProgressFrequency.DAILY))
    }

    @Test
    fun `convert ONCE_IN_3_DAYS enum to string`() {
        assertEquals("ONCE_IN_3_DAYS", converter.fromProgressFrequency(ProgressFrequency.ONCE_IN_3_DAYS))
    }

    @Test
    fun `convert WEEKLY enum to string`() {
        assertEquals("WEEKLY", converter.fromProgressFrequency(ProgressFrequency.WEEKLY))
    }

    @Test
    fun `convert ONCE_IN_2_WEEKS enum to string`() {
        assertEquals("ONCE_IN_2_WEEKS", converter.fromProgressFrequency(ProgressFrequency.ONCE_IN_2_WEEKS))
    }

    @Test
    fun `convert DAILY string to enum`() {
        assertEquals(ProgressFrequency.DAILY, converter.toProgressFrequency("DAILY"))
    }

    @Test
    fun `convert ONCE_IN_3_DAYS string to enum`() {
        assertEquals(ProgressFrequency.ONCE_IN_3_DAYS, converter.toProgressFrequency("ONCE_IN_3_DAYS"))
    }

    @Test
    fun `convert WEEKLY string to enum`() {
        assertEquals(ProgressFrequency.WEEKLY, converter.toProgressFrequency("WEEKLY"))
    }

    @Test
    fun `convert ONCE_IN_2_WEEKS string to enum`() {
        assertEquals(ProgressFrequency.ONCE_IN_2_WEEKS, converter.toProgressFrequency("ONCE_IN_2_WEEKS"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throw on invalid string value`() {
        converter.toProgressFrequency("INVALID")
    }
}