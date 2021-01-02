package com.wutsi.core.util

import org.apache.commons.lang.time.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek.SUNDAY
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


object DateUtils {
    fun year(date: Date) = toCalendar(date).get(Calendar.YEAR)

    fun month(date: Date) = toCalendar(date).get(Calendar.MONTH)

    fun toCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getTimeZone("UTC")
        cal.time = date
        return cal
    }

    fun beginingOfTheDay(date: Date) = toDate(toLocalDate(date))

    fun endOfTheDay(date: Date) = addMinutes(
        date = addDays(beginingOfTheDay(date), 1),
        offset = -1
    )

    fun beginingOfTheWeek(date: LocalDate): LocalDate {
        var cur = date
        while (cur.dayOfWeek != SUNDAY)
            cur = cur.plusDays(-1)
        return cur
    }


    fun addDays(date: Date, offset: Int) = DateUtils.addDays(date, offset)

    fun addMonths(date: Date, offset: Int) = DateUtils.addMonths(date, offset)

    fun addMinutes(date: Date, offset: Int) = DateUtils.addMinutes(date, offset)

    fun addHours(date: Date, offset: Int) = DateUtils.addHours(date, offset)

    fun yesterday() = DateUtils.addDays(Date(), -1)

    fun format(date: Date, pattern: String): String = createDateFormat(pattern).format(date)

    fun toLocalDate(date: Date) = date.toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()

    fun toDate(date: LocalDate) = Date.from(date
            .atStartOfDay(ZoneId.of("UTC"))
            .toInstant()
    )

    fun toDate(year: Int, month: Int, day: Int): Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month-1)
        cal.set(Calendar.DAY_OF_MONTH, day)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }

    fun toLocalDate(year: Int, month: Int, day: Int) = LocalDate.of(year, month, day)

    fun createDateFormat(pattern: String): DateFormat {
        val fmt = SimpleDateFormat(pattern)
        fmt.timeZone = TimeZone.getTimeZone("UTC")
        return fmt
    }
}
