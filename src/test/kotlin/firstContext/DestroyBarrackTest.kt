package firstContext

import BaseTest
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual

class DestroyBarrackTest : BaseTest<DestroyBarrack>() {

    override val testComponent: DestroyBarrack = DestroyBarrack()

    init {
        context("test destroy barrak test contest") {
            withData(
                InputData(startUnits = 10, barrackHealth = 11, productionEnemyUnit = 15, result = 4),
                InputData(startUnits = 1, barrackHealth = 2, productionEnemyUnit = 1, result = -1),
                InputData(startUnits = 1, barrackHealth = 1, productionEnemyUnit = 1, result = 1),
                InputData(startUnits = 25, barrackHealth = 200, productionEnemyUnit = 10, result = 13),
                InputData(startUnits = 25, barrackHealth = 50, productionEnemyUnit = 1, result = 3),
                InputData(startUnits = 250, barrackHealth = 500, productionEnemyUnit = 208, result = 5),
                InputData(startUnits = 250, barrackHealth = 500, productionEnemyUnit = 249, result = 101),
                InputData(startUnits = 13, barrackHealth = 81, productionEnemyUnit = 10, result = 23),
                InputData(startUnits = 300, barrackHealth = 301, productionEnemyUnit = 484, result = 6),
            ) { it.test() }
        }
    }

    private fun InputData.test() {
        testComponent.getDestroyMinStep(
            startUnits,
            barrackHealth,
            productionEnemyUnit
        ) shouldBeEqual result
    }

    private data class InputData(
        val startUnits: Long,
        val barrackHealth: Long,
        val productionEnemyUnit: Long,
        val result: Long
    )
}