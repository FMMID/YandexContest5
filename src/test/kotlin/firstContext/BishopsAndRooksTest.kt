package firstContext

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import java.io.File

class BishopsAndRooksTest : FunSpec() {

    private val bishopsAndRooks = BishopsAndRooks()

    init {
        context("test task bishops and rooks") {
            withData(
                InputData(matrix = readMatrixFormFile("Example1.txt"), result = 49),
                InputData(matrix = readMatrixFormFile("Example2.txt"), result = 54),
                InputData(matrix = readMatrixFormFile("Example3.txt"), result = 40),
                InputData(matrix = readMatrixFormFile("Example4.txt"), result = 42),
            ) { inputData ->
                bishopsAndRooks.calculateFreeCells(inputData.matrix) shouldBeEqual inputData.result
            }
        }

        test("first example") {
            InputData(matrix = readMatrixFormFile("Example1.txt"), result = 49).let {
                bishopsAndRooks.calculateFreeCells(it.matrix) shouldBeEqual it.result
            }
        }

        test("third example") {
            InputData(matrix = readMatrixFormFile("Example3.txt"), result = 40).let {
                bishopsAndRooks.calculateFreeCells(it.matrix) shouldBeEqual it.result
            }
        }
    }

    private fun readMatrixFormFile(fileName: String): Array<CharArray> {
        val file = File("D:\\Projectes\\Algorithm\\src\\test\\resources\\bishopsAndRooks\\$fileName")

        val mutableArray = mutableListOf<CharArray>()

        file.forEachLine { line ->
            mutableArray.add(line.trim().toCharArray())
        }

        println("\narray:\n" + mutableArray.joinToString("\n") {
            it.joinToString(", ")
        } + "\n")

        return mutableArray.toTypedArray()
    }

    private data class InputData(val matrix: Array<CharArray>, val result: Int)
}