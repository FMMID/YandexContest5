package secondContext

class FishSeller {

    fun calculateMaxProfit(fishSpoilageDays: Long, listOfFishPrice: List<Long>): Long {
        var maxProfit = 0L

        for (day in listOfFishPrice.indices) {
            val buyFish = listOfFishPrice[day]

            val formDay = if (day + 1 >= listOfFishPrice.size) {
                listOfFishPrice.size - 1
            } else {
                day + 1
            }

            val toDay = if (day + fishSpoilageDays >= listOfFishPrice.size) {
                listOfFishPrice.size
            } else {
                day + fishSpoilageDays + 1
            }.toInt()

            var localMax = Long.MIN_VALUE

            if (formDay == toDay) break

            listOfFishPrice.subList(formDay, toDay).forEach { currentPrice ->
                localMax = maxOf(localMax, currentPrice)
            }

            val localProfit = localMax - buyFish

            maxProfit = maxOf(maxProfit, localProfit)
        }

        return maxProfit
    }
}