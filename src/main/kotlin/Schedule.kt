import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class Schedule {
    fun getMinMaxDayOfWeek(currentYear: Int, holidays: List<Holiday>, firstDayOfJanuary: DayOfWeek): ScheduleResult {
        val isLeapYear = currentYear % 400 == 0 || currentYear % 4 == 0 && currentYear % 100 != 0
        val availableDays: Int = if (isLeapYear) 366 else 365
        val mapOfHolidays = mutableMapOf<DayOfWeek, Int>()
        val mapOfCountDayOfWeeks = mutableMapOf<DayOfWeek, Int>()

        holidays.forEach { holiday ->
            val date = LocalDate.of(currentYear, holiday.month, holiday.day)
            mapOfHolidays[date.dayOfWeek] = mapOfHolidays[date.dayOfWeek]?.plus(1) ?: 1
        }

        countDaysOfWeeks(mapOfCountDayOfWeeks, availableDays, firstDayOfJanuary)

        mapOfCountDayOfWeeks.forEach { (dayOfWeek, _) ->
            val dayOfWeekCount = mapOfCountDayOfWeeks[dayOfWeek]
            val dayOfWeekHolidayCount = mapOfHolidays[dayOfWeek]
            if (dayOfWeekCount != null && dayOfWeekHolidayCount != null) {
                mapOfCountDayOfWeeks[dayOfWeek] = dayOfWeekCount - dayOfWeekHolidayCount
            }
        }

        val maxOfFreeDaysDayOfWeek = mapOfCountDayOfWeeks.maxBy { it.value }
        val minOfFreeDaysDayOfWeek = mapOfCountDayOfWeeks.minBy { it.value }

        return ScheduleResult(maxOfFreeDaysDayOfWeek.key, minOfFreeDaysDayOfWeek.key)
    }

    private fun countDaysOfWeeks(
        mapOfDaysOfWeek: MutableMap<DayOfWeek, Int>,
        availableDays: Int,
        currentDayOfWeek: DayOfWeek
    ) {
        if (availableDays <= 0) return
        mapOfDaysOfWeek[currentDayOfWeek] = mapOfDaysOfWeek[currentDayOfWeek]?.plus(1) ?: 1
        val updatedAvailableDays = availableDays - 1
        countDaysOfWeeks(mapOfDaysOfWeek, updatedAvailableDays, getNextDayOfWeek(currentDayOfWeek))
    }

    private fun getNextDayOfWeek(dayOfWeek: DayOfWeek): DayOfWeek {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> DayOfWeek.TUESDAY
            DayOfWeek.TUESDAY -> DayOfWeek.WEDNESDAY
            DayOfWeek.WEDNESDAY -> DayOfWeek.THURSDAY
            DayOfWeek.THURSDAY -> DayOfWeek.FRIDAY
            DayOfWeek.FRIDAY -> DayOfWeek.SATURDAY
            DayOfWeek.SATURDAY -> DayOfWeek.SUNDAY
            DayOfWeek.SUNDAY -> DayOfWeek.MONDAY
        }
    }

    data class Holiday(val day: Int, val month: Int)

    data class ScheduleResult(
        private val maxOfFreeDaysDayOfWeek: DayOfWeek,
        private val minOfFreeDaysDayOfWeek: DayOfWeek
    ) {

        fun printResult() {
            val formattedMin = maxOfFreeDaysDayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            val formattedMax = minOfFreeDaysDayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            println("$formattedMin $formattedMax")
        }
    }
}