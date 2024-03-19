package secondContext

import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import java.io.File
import java.math.BigInteger

class BestBlockTest : BaseTest<BestBlock>() {
    override val testComponent: BestBlock
        get() = BestBlock()

    init {
        context("test context") {
            withData(
                readTestData("Test1.txt").toInputData("2 2"),
                readTestData("Test2.txt").toInputData("3 2"),
                readTestData("Test7.txt").toInputData("3 2"),
                readTestData("Test8.txt").toInputData("4 3"),
                readTestData("Test9.txt").toInputData("4 4"),
                readTestData("Test13.txt").toInputData("4 4"),
            ) { it.test() }
        }
    }

    private fun InputData.test() {
        testComponent.calculateBlockRaceAndClass(
            heroesTable, races, classes
        ) shouldBeEqual result
    }

    private class InputData(
        val heroesTable: Array<Array<BigInteger>>,
        val races: Int,
        val classes: Int,
        val result: String
    )

    private fun Triple<Array<Array<BigInteger>>, Int, Int>.toInputData(result: String): InputData {
        return InputData(
            first,
            second,
            third,
            result
        )
    }

    private fun readTestData(fileName: String): Triple<Array<Array<BigInteger>>, Int, Int> {
        val file = File("D:\\Projectes\\Algorithm\\src\\test\\resources\\bestBlock\\$fileName")

        val lines = file.readLines()

        val (races, classes) = lines.first().split(' ').map(String::toInt)
        val heroesTable: ArrayList<Array<BigInteger>> = arrayListOf()

        lines.forEachIndexed { index, line ->
            if (index != 0) {
                heroesTable.add(line.split(' ').map(String::toBigInteger).toTypedArray())
            }
        }

        return Triple(heroesTable.toTypedArray(), races, classes)
    }
}