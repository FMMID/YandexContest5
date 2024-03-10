package firstContext.paintingTrees

import kotlin.math.abs

class PaintingTrees {
    fun getPaintedTreesCount(vStartPoint: Int, vAllowDistance: Int, mStartPoint: Int, mAllowDistance: Int): Int {

        if (vStartPoint == mStartPoint && vAllowDistance > mAllowDistance) {
            vAllowDistance.paintedTrees()
        }

        if (vStartPoint == mStartPoint && vAllowDistance < mAllowDistance) {
            mAllowDistance.paintedTrees()
        }

        val distanceBetween = abs(vStartPoint - mStartPoint)

        if (distanceBetween > vAllowDistance + mAllowDistance) {
            return vAllowDistance.paintedTrees() + mAllowDistance.paintedTrees()
        }

        val vRightEdge = vStartPoint + vAllowDistance
        val vLeftEdge = vStartPoint - vAllowDistance

        val mRightEdge = mStartPoint + mAllowDistance
        val mLeftEdge = mStartPoint - mAllowDistance

        if (vRightEdge > mRightEdge && vLeftEdge < mLeftEdge) {
            return vAllowDistance.paintedTrees()
        }

        if (mRightEdge > vRightEdge && mLeftEdge < vLeftEdge) {
            return mAllowDistance.paintedTrees()
        }

        return if (vAllowDistance > mAllowDistance) {
            val halfDistanceFowM = distanceBetween - vAllowDistance
            vAllowDistance.paintedTrees() + mAllowDistance + halfDistanceFowM
        } else {
            val halfDistanceFowM = distanceBetween - mAllowDistance
            mAllowDistance.paintedTrees() + vAllowDistance + halfDistanceFowM
        }
    }

    companion object {
        private const val PAINTING_IN_BOTH_DIRECTIONS = 2
        private const val START_TREE = 1
    }

    private fun Int.paintedTrees(): Int {
        return this.let { allowDistance ->
            allowDistance * PAINTING_IN_BOTH_DIRECTIONS + START_TREE
        }
    }
}