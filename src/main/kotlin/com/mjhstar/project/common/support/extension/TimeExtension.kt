package com.mjhstar.project.common.support.extension

import java.time.*
import java.time.format.DateTimeFormatter

private val ZONE_ID_ASIA_SEOUL = ZoneId.of("Asia/Seoul")

object TimeUtils {
    fun currentTimeMillis() = System.currentTimeMillis()
    fun currentOffsetDateTime() = currentTimeMillis().toOffsetDateTime()
    fun currentLocalDate() = LocalDate.now()
    fun todayMillis() = LocalDate.now().toMillis()
}

fun Long.plusYear(year: Long): Long {
    val today = this.toOffsetDateTime()
    return today.plusYears(year).toMillis()
}

fun String.toLocalDate(): LocalDate {
    val dateRegex1 = Regex("^\\d{4}\\.\\d{2}\\.\\d{2}\$")
    val dateRegex2 = Regex("^\\d{8}\$")
    val dateRegex3 = Regex("^\\d{4}/\\d{2}/\\d{2}\$")
    return when {
        dateRegex1.matches(this) -> LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        dateRegex2.matches(this) -> LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyyMMdd"))
        dateRegex3.matches(this) -> LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        else -> LocalDate.parse(this)
    }
}

fun LocalDate.toMillis() =
    this.atStartOfDay(ZONE_ID_ASIA_SEOUL).toInstant().toEpochMilli()

fun OffsetDateTime.toMillis() =
    this.toInstant().toEpochMilli()

fun Long.toOffsetDateTime(): OffsetDateTime {
    val instant = Instant.ofEpochMilli(this)
    return OffsetDateTime.ofInstant(instant, ZONE_ID_ASIA_SEOUL)
}
