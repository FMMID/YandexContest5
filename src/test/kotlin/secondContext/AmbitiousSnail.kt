package secondContext

import AmbitiousSnail
import AmbitiousSnail.Berry
import AmbitiousSnail.ResultHeight
import BaseTest
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class AmbitiousSnailTest() : BaseTest<AmbitiousSnail>() {
    override val testComponent: AmbitiousSnail = AmbitiousSnail()

    init {
        context("test ambitious snail context") {
            withData(
                InputData(
                    listOf(
                        Berry(2, 1, 1),
                        Berry(5, 2, 2)
                    ), ResultHeight(6, listOf(1, 2))
                ),
                InputData(
                    listOf(
                        Berry(lift = 1, decline = 5, order = 1),
                        Berry(lift = 8, decline = 2, order = 2),
                        Berry(lift = 4, decline = 4, order = 3)
                    ), ResultHeight(10, listOf(2, 3, 1))
                ),
                InputData(
                    listOf(
                        Berry(lift = 7, decline = 6, order = 1),
                        Berry(lift = 7, decline = 4, order = 2),
                    ), ResultHeight(10, listOf(2, 1))
                ),
                InputData(
                    listOf(
                        Berry(lift = 160714711, decline = 449656269, order = 1),
                        Berry(lift = 822889311, decline = 446755913, order = 2),
                        Berry(lift = 135599877, decline = 389312924, order = 3),
                        Berry(lift = 448565595, decline = 480845266, order = 4),
                        Berry(lift = 561330066, decline = 605997004, order = 5),
                        Berry(lift = 61020590, decline = 573085537, order = 6),
                        Berry(lift = 715477619, decline = 181424399, order = 7),
                    ), ResultHeight(1471516684, listOf(2, 7, 5, 1, 3, 4, 6))
                ),
                InputData(
                    listOf(
                        Berry(lift = 160714711, decline = 449656269, order = 1),
                        Berry(lift = 822889311, decline = 446755913, order = 2),
                        Berry(lift = 135599877, decline = 389312924, order = 3),
                        Berry(lift = 480845266, decline = 448565595, order = 4),
                        Berry(lift = 561330066, decline = 605997004, order = 5),
                        Berry(lift = 61020590, decline = 573085537, order = 6),
                        Berry(lift = 715477619, decline = 181424399, order = 7),
                    ), ResultHeight(1503796355, listOf(2, 4, 7, 5, 1, 3, 6))
                ),
            ) { it.test() }
        }
    }

    private fun InputData.test() {
        testComponent.calculateHeight(list).maxHeight shouldBeEqual result.maxHeight
    }

    private data class InputData(val list: List<Berry>, val result: ResultHeight)

}