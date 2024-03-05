import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.single
import paintingTrees.PaintingTrees
import paintingTrees.WorkedPaintingTrees

class PaintingTreesTest : FunSpec() {

    private val paintingTrees = PaintingTrees()
    private val workedPaintingTrees = WorkedPaintingTrees()

    init {
        context("test painting trees") {
            withData(
                InputData(vStartPoint = 0, vAllowDistance = 7, mStartPoint = 12, mAllowDistance = 5, result = 25),
                InputData(vStartPoint = 0, vAllowDistance = 4, mStartPoint = 2, mAllowDistance = 3, result = 10),
                InputData(vStartPoint = 0, vAllowDistance = 4, mStartPoint = 3, mAllowDistance = 3, result = 11),
                InputData(vStartPoint = 0, vAllowDistance = 4, mStartPoint = 0, mAllowDistance = 3, result = 9),
                InputData(vStartPoint = 0, vAllowDistance = 2, mStartPoint = 1, mAllowDistance = 1, result = 5),
                InputData(vStartPoint = 0, vAllowDistance = 4, mStartPoint = 4, mAllowDistance = 3, result = 12),
                InputData(vStartPoint = -5, vAllowDistance = 3, mStartPoint = -3, mAllowDistance = 4, result = 10),
                InputData(vStartPoint = -8, vAllowDistance = 2, mStartPoint = 2, mAllowDistance = 4, result = 14),
            ) { inputData ->
                paintingTrees.getPaintedTreesCount(
                    inputData.vStartPoint,
                    inputData.vAllowDistance,
                    inputData.mStartPoint,
                    inputData.mAllowDistance
                ).shouldBeEqual(inputData.result)
            }
        }

        context("test painting trees generated") {
            withData(
                List(10000) {
                    val vStartPoint = Arb.int(-100..100).single()
                    val vAllowDistance = Arb.int(0..100).single()
                    val mStartPoint = Arb.int(-100..100).single()
                    val mAllowDistance = Arb.int(0..100).single()
                    val result = workedPaintingTrees.getPaintedTreesCount(
                        vStartPoint,
                        vAllowDistance,
                        mStartPoint,
                        mAllowDistance
                    )

                    InputData(vStartPoint, vAllowDistance, mStartPoint, mAllowDistance, result)
                }
            ) { inputData ->
                paintingTrees.getPaintedTreesCount(
                    inputData.vStartPoint,
                    inputData.vAllowDistance,
                    inputData.mStartPoint,
                    inputData.mAllowDistance
                ).shouldBeEqual(inputData.result)
            }
        }
    }

    private data class InputData(
        val vStartPoint: Int,
        val vAllowDistance: Int,
        val mStartPoint: Int,
        val mAllowDistance: Int,
        val result: Int
    )
}
