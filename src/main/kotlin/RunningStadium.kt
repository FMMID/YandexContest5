class RunningStadium {

    fun calculateTimeRunning(stadiumLength: Long, kirill: Runner, anton: Runner): RunningResult {

        if (kirill.startDistance == anton.startDistance) {
            return RunningResult(isDistanceToStartMatched = true, minMatchTime = 0.00000000)
        }

        if (kirill.currentSpeed == 0L && anton.currentSpeed == 0L) {
            return RunningResult(isDistanceToStartMatched = false, minMatchTime = 0.00000000)
        }

        var dt1: Double
        var dt2: Double
        var resultDt = Double.MAX_VALUE

        for (cycle in -1000..1000) {
            dt1 = firstTimeDelta(stadiumLength, cycle.toLong(), kirill, anton)
            dt2 = secondTimeDelta(stadiumLength, cycle.toLong(), kirill, anton)

            resultDt = if (dt1 >= 0.0 && dt2 >= 0.0) {
                minOf(dt1, dt2, resultDt)
            } else if (dt1 <= 0.0 && dt2 >= 0.0) {
                minOf(dt2, resultDt)
            } else if (dt1 >= 0.0 && dt2 <= 0.0) {
                minOf(dt1, resultDt)
            } else {
                resultDt
            }
        }

        return if (resultDt > 0.0) {
            RunningResult(isDistanceToStartMatched = true, minMatchTime = resultDt)
        } else {
            RunningResult(isDistanceToStartMatched = false, minMatchTime = 0.0)
        }
    }

    private fun firstTimeDelta(stadiumLength: Long, n: Long, kirill: Runner, anton: Runner): Double {
        return (stadiumLength * n - (kirill.startDistance + anton.startDistance)) / (kirill.currentSpeed + anton.currentSpeed).toDouble()
    }

    private fun secondTimeDelta(stadiumLength: Long, n: Long, kirill: Runner, anton: Runner): Double {
        return if (kirill.currentSpeed != anton.currentSpeed) {
            (stadiumLength * n - (kirill.startDistance - anton.startDistance)) / (kirill.currentSpeed - anton.currentSpeed).toDouble()
        } else {
            -1.0
        }
    }

    data class RunningResult(val isDistanceToStartMatched: Boolean, val minMatchTime: Double) {

        fun printResult() {
            if (isDistanceToStartMatched) {
                println("YES")
                println(String.format("%.10f", minMatchTime))
            } else {
                println("NO")
            }
        }
    }

    data class Runner(val startDistance: Long, val currentSpeed: Long)
}