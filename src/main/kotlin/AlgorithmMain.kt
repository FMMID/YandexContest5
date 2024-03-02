fun main() {
    val (vStartPoint, vAllowDistance) = readLine()!!.split(' ').map(String::toInt)
    val (mStartPoint, mAllowDistance) = readLine()!!.split(' ').map(String::toInt)
    val paintedTrees = PaintingTrees().getPaintedTreesCount(vStartPoint, vAllowDistance, mStartPoint, mAllowDistance)
    print(paintedTrees)
}