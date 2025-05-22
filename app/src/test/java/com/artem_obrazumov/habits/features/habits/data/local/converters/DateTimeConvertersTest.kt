package com.artem_obrazumov.habits.features.habits.data.local.converters

import org.junit.Test
import java.time.LocalDate
import org.junit.Assert.assertEquals
import java.time.LocalDateTime

class DateTimeConvertersTest {

    private val converter = DateTimeConverter()

    @Test
    fun `converts date to string`() {
        val date = LocalDate.of(2025, 5, 1)
        val string = converter.fromLocalDate(date)
        assertEquals("2025-05-01", string)
    }

    @Test
    fun `converts string to date`() {
        val date = converter.toLocalDate("2025-05-01")
        assertEquals(date, LocalDate.of(2025, 5, 1))
    }

    @Test
    fun `converts date and time to string`() {
        val dateTime = LocalDateTime.of(2025, 5, 1, 12, 30, 2)
        val string = converter.fromLocalDateTime(dateTime)
        assertEquals("2025-05-01T12:30:02", string)
    }

    @Test
    fun `converts string to date and time`() {
        val dateTime = converter.toLocalDateTime("2025-05-01T12:30:02")
        assertEquals(dateTime, LocalDateTime.of(2025, 5, 1, 12, 30, 2))
    }
}