fun main() {
    val playerUnits = readLine()!!.toString().toLong()
    val barrackHealth = readLine()!!.toString().toLong()
    val productionEnemyUnits = readLine()!!.toString().toLong()

    val minDestroyBarrackStep = DestroyBarrack().getDestroyMinStep(playerUnits, barrackHealth, productionEnemyUnits)

    println(minDestroyBarrackStep)
}

