package firstContext

import BaseTest
import firstContext.Schedule.Holiday
import firstContext.Schedule.ScheduleResult
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import java.time.DayOfWeek

class ScheduleTest : BaseTest<Schedule>() {

    override val testComponent: Schedule = Schedule()

    init {
        context("test schedule task context") {
            withData(
                InputData(
                    currentYear = 2015,
                    holidays = listOf(Holiday(day = 1, month = 1), Holiday(day = 8, month = 1)),
                    firstDayOfJanuary = DayOfWeek.THURSDAY,
                    result = ScheduleResult(DayOfWeek.FRIDAY, DayOfWeek.THURSDAY)
                ),
                InputData(
                    currentYear = 2013,
                    holidays = listOf(Holiday(day = 1, month = 1), Holiday(day = 8, month = 1)),
                    firstDayOfJanuary = DayOfWeek.TUESDAY,
                    result = ScheduleResult(DayOfWeek.WEDNESDAY, DayOfWeek.TUESDAY)
                ),
                InputData(
                    currentYear = 2013,
                    holidays = listOf(
                        Holiday(day = 6, month = 2),
                        Holiday(day = 13, month = 2),
                        Holiday(day = 20, month = 2)
                    ),
                    firstDayOfJanuary = DayOfWeek.TUESDAY,
                    result = ScheduleResult(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY)
                ),
            ) { it.test() }
        }
    }

    private fun InputData.test() {
        testComponent.getMinMaxDayOfWeek(
            currentYear,
            holidays,
            firstDayOfJanuary
        ) shouldBeEqual result
    }

    private data class InputData(
        val currentYear: Int,
        val holidays: List<Holiday>,
        val firstDayOfJanuary: DayOfWeek,
        val result: ScheduleResult
    )
}