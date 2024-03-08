import kotlin.math.abs

class DestroyBarrack {
    fun getDestroyMinStep(playerUnits: Long, barrackHealth: Long, productionEnemyUnits: Long): Long {
        var currentGameStep = 1L
        var currentPlayerUnits = playerUnits
        var currentEnemyBarrackHealth = barrackHealth - currentPlayerUnits
        var currentEnemyUnits = 0L

        if (currentEnemyBarrackHealth <= 0) return currentGameStep

        while (true) {
            currentGameStep++
            currentEnemyUnits += if (currentEnemyBarrackHealth > 0) productionEnemyUnits else 0

            if (isWinBarrackAttack(currentPlayerUnits, currentEnemyUnits, currentEnemyBarrackHealth)) {
                val playerAvailableUnits = currentPlayerUnits - currentEnemyBarrackHealth

                currentEnemyBarrackHealth = 0
                currentEnemyUnits = maxOf(currentEnemyUnits - playerAvailableUnits, 0)
            } else {
                val restOfEnemies = currentEnemyUnits - currentPlayerUnits
                if (restOfEnemies >= 0) break
                currentEnemyUnits = 0

                val playerAvailableUnits = abs(restOfEnemies)
                currentEnemyBarrackHealth -= playerAvailableUnits
            }

            currentPlayerUnits -= currentEnemyUnits

            if (currentPlayerUnits <= 0L || isEnemyDestroyed(currentEnemyUnits, currentEnemyBarrackHealth)) break
        }

        return if (isEnemyDestroyed(currentEnemyUnits, currentEnemyBarrackHealth) && currentPlayerUnits > 0) {
            currentGameStep
        } else {
            -1L
        }
    }

    private fun isEnemyDestroyed(
        currentEnemyUnits: Long,
        currentEnemyBarrackHealth: Long
    ): Boolean {
        return currentEnemyBarrackHealth <= 0L && currentEnemyUnits <= 0L
    }

    private fun isWinBarrackAttack(
        currentPlayerUnits: Long,
        currentEnemyUnits: Long,
        currentEnemyBarrackHealth: Long
    ): Boolean {
        val healthBarrackAfterAttack = currentEnemyBarrackHealth - currentPlayerUnits
        if (healthBarrackAfterAttack > 0) return false

        var restOfEnemyUnits = currentEnemyUnits - abs(healthBarrackAfterAttack)

        val firstBattleResult = getABattleResult(
            currentEnemyUnits = restOfEnemyUnits,
            currentPlayerUnits = currentPlayerUnits,
            isPlayerFirst = false
        )

        restOfEnemyUnits = currentEnemyUnits - currentPlayerUnits
        if (restOfEnemyUnits < 0) {
            val playerAvailableUnits = abs(restOfEnemyUnits)
            val nextEnemyBarrackHealth = currentEnemyBarrackHealth - playerAvailableUnits
            restOfEnemyUnits = currentEnemyUnits - (currentPlayerUnits - nextEnemyBarrackHealth)

            val secondBattleResult = getABattleResult(
                currentEnemyUnits = restOfEnemyUnits,
                currentPlayerUnits = currentPlayerUnits,
                isPlayerFirst = false
            )

            return firstBattleResult.isPlayerWin && secondBattleResult.isPlayerWin && firstBattleResult.countOfBattle <= secondBattleResult.countOfBattle
        }

        return firstBattleResult.isPlayerWin
    }

    private fun getABattleResult(
        currentEnemyUnits: Long,
        currentPlayerUnits: Long,
        isPlayerFirst: Boolean,
        countOfBattle: Long = 0L
    ): BattleResult {
        if (currentPlayerUnits > 0L && currentEnemyUnits <= 0L) {
            return BattleResult(
                isPlayerWin = true,
                countOfBattle = countOfBattle / 2
            )
        }

        if (currentPlayerUnits <= 0L && currentEnemyUnits > 0L) {
            return BattleResult(
                isPlayerWin = false,
                countOfBattle = countOfBattle / 2
            )
        }

        return if (isPlayerFirst) {
            val restOfEnemyUnits = currentEnemyUnits - currentPlayerUnits
            getABattleResult(
                currentEnemyUnits = restOfEnemyUnits,
                currentPlayerUnits = currentPlayerUnits,
                isPlayerFirst = false,
                countOfBattle = countOfBattle + 1
            )
        } else {
            val restOfPlayerUnits = currentPlayerUnits - currentEnemyUnits
            getABattleResult(
                currentEnemyUnits = currentEnemyUnits,
                currentPlayerUnits = restOfPlayerUnits,
                isPlayerFirst = true,
                countOfBattle = countOfBattle + 1
            )
        }
    }

    private data class BattleResult(val isPlayerWin: Boolean, val countOfBattle: Long)
}