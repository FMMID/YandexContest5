package paintingTrees

class WorkedPaintingTrees {

    fun getPaintedTreesCount(vStartPoint: Int, vAllowDistance: Int, mStartPoint: Int, mAllowDistance: Int): Int {

        val setOfPaintedTrees = mutableSetOf<Int>()

        setOfPaintedTrees.addAll(vStartPoint - vAllowDistance..vStartPoint + vAllowDistance)
        setOfPaintedTrees.addAll(mStartPoint - mAllowDistance..mStartPoint + mAllowDistance)

        return setOfPaintedTrees.size
    }
}