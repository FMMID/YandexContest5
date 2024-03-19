package secondContext

import java.math.BigInteger


class BestBlock {
    fun calculateBlockRaceAndClass(heroesTable: Array<Array<BigInteger>>, races: Int, classes: Int): String {
        val scopeOfMaxes = hashMapOf<Hero, BigInteger>()

        for (race in 0 until races) {
            var localMax = BigInteger.ZERO
            var localMaxClassIndex = 0

            for (clas in 0 until classes) {
                val currentValue = heroesTable[race][clas]
                if (currentValue > localMax) {
                    localMax = currentValue
                    localMaxClassIndex = clas
                }
            }

            scopeOfMaxes.put(Hero(race, localMaxClassIndex), localMax)
        }

        for (clas in 0 until classes) {
            var localMax = BigInteger.ZERO
            var localMaxRaceIndex = 0

            for (race in 0 until races) {
                val currentValue = heroesTable[race][clas]
                if (currentValue > localMax) {
                    localMax = currentValue
                    localMaxRaceIndex = race
                }
            }

            scopeOfMaxes.put(Hero(localMaxRaceIndex, clas), localMax)
        }

        val groupedRace = scopeOfMaxes.entries.groupBy { it.key.race }
        val groupedClass = scopeOfMaxes.entries.groupBy { it.key.clas }

        val maxHeroRace = groupedRace.maxBy { (_, list) -> list.maxOf { it.value } }
        val maxHeroRaceClass = groupedClass.maxBy { (_, list) ->
            val filteredList = list.filter { it.key.race != maxHeroRace.key }
            if (filteredList.isEmpty()) return@maxBy BigInteger.ZERO
            filteredList.maxOf { it.value }
        }

        val maxHeroClass = groupedClass.maxBy { (_, list) -> list.maxOf { it.value } }
        val maxHeroClassRace = groupedRace.maxBy { (_, list) ->
            val filteredList = list.filter { it.key.clas != maxHeroClass.key }
            if (filteredList.isEmpty()) return@maxBy BigInteger.ZERO
            filteredList.maxOf { it.value }
        }

        val firstTakeRaceBest = maxHeroRace.value.sumOf { it.value }
        val firstTakeClassBest = maxHeroClass.value.sumOf { it.value }

        val secondTakeClassBest = maxHeroRaceClass.value.sumOf { it.value }
        val secondTakeRaceBest = maxHeroClassRace.value.sumOf { it.value }

        return when {
            firstTakeRaceBest == firstTakeClassBest -> {
                if (secondTakeClassBest > secondTakeRaceBest) {
                    "${maxHeroRace.key + 1} ${maxHeroRaceClass.key + 1}"
                } else {
                    "${maxHeroClassRace.key + 1} ${maxHeroClass.key + 1}"
                }
            }

            firstTakeRaceBest > firstTakeClassBest -> {
                "${maxHeroRace.key + 1} ${maxHeroRaceClass.key + 1}"
            }

            else -> {
                "${maxHeroClassRace.key + 1} ${maxHeroClass.key + 1}"
            }
        }
    }
}

data class Hero(val race: Int, val clas: Int)