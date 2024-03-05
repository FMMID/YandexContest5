class BishopsAndRooks {

    /**
     * Ладья бьет все клетки горизонтали и вертикали, проходящих через клетку, где она стоит, до первой встретившейся фигуры.
     * Слон бьет все клетки обеих диагоналей, проходящих через клетку, где он стоит, до первой встретившейся фигуры.
     */
    fun calculateFreeCells(matrix: Array<CharArray>): Int {
        val matrixSize = matrix.size
        var closedCells = 0

        for (i in matrix.indices) {
            for (j in matrix.indices) {

                if (matrix[i][j] == ChessPiece.ROOK.value) {
                    var canContinueTop = i != 0
                    var canContinueBottom = i != matrixSize - 1
                    var canContinueLeft = j != 0
                    var canContinueRight = j != matrixSize - 1

                    closedCells++

                    for (index in 1..<matrixSize) {
                        val topBorderIndex = i - index
                        val bottomBorderIndex = i + index
                        val leftBorderIndex = j - index
                        val rightBorderIndex = j + index

                        if (canContinueTop) {
                            closedCells += matrix.calculateClosedCalls(row = topBorderIndex, col = j)

                            canContinueTop = matrix[topBorderIndex][j].canContinueCalculateCells(
                                additionalCondition = topBorderIndex != 0
                            )
                        }

                        if (canContinueBottom) {
                            closedCells += matrix.calculateClosedCalls(row = bottomBorderIndex, col = j)

                            canContinueBottom = matrix[bottomBorderIndex][j].canContinueCalculateCells(
                                additionalCondition = bottomBorderIndex != matrixSize - 1
                            )
                        }

                        if (canContinueLeft) {
                            closedCells += matrix.calculateClosedCalls(row = i, col = leftBorderIndex)

                            canContinueLeft = matrix[i][leftBorderIndex].canContinueCalculateCells(
                                additionalCondition = leftBorderIndex != 0
                            )
                        }

                        if (canContinueRight) {
                            closedCells += matrix.calculateClosedCalls(row = i, col = rightBorderIndex)

                            canContinueRight = matrix[i][rightBorderIndex].canContinueCalculateCells(
                                additionalCondition = rightBorderIndex != matrixSize - 1
                            )
                        }

                        matrix.printMatrix()

                        if (!canContinueTop && !canContinueBottom && !canContinueLeft && !canContinueRight) break
                    }
                }

                if (matrix[i][j] == ChessPiece.BISHOP.value) {
                    var canContinueTopLeft = i != 0 && j != 0
                    var canContinueBottomRight = i != matrixSize - 1 && j != matrixSize - 1
                    var canContinueTopRight = i != 0 && j != matrixSize - 1
                    var canContinueBottomLeft = i != matrixSize - 1 && j != 0

                    closedCells++

                    for (index in 1..<matrixSize) {
                        val leftTopRow = i - index
                        val leftTopCol = j - index
                        val rightBottomRow = i + index
                        val rightBottomCol = j + index

                        val rightTopRow = i - index
                        val rightTopCol = j + index
                        val leftBottomRow = i + index
                        val leftBottomCol = j - index


                        if (canContinueTopLeft) {
                            closedCells += matrix.calculateClosedCalls(row = leftTopRow, col = leftTopCol)

                            canContinueTopLeft = matrix[leftTopRow][leftTopCol].canContinueCalculateCells(
                                additionalCondition = leftTopRow != 0 && leftTopCol != 0
                            )
                        }

                        if (canContinueBottomRight) {
                            closedCells += matrix.calculateClosedCalls(row = rightBottomRow, col = rightBottomCol)

                            canContinueBottomRight = matrix[rightBottomRow][rightBottomCol].canContinueCalculateCells(
                                additionalCondition = rightBottomRow != matrixSize - 1 && rightBottomCol != matrixSize - 1
                            )
                        }

                        if (canContinueTopRight) {
                            closedCells += matrix.calculateClosedCalls(row = rightTopRow, col = rightTopCol)

                            canContinueTopRight = matrix[rightTopRow][rightTopCol].canContinueCalculateCells(
                                additionalCondition = rightTopRow != 0 && rightTopCol != matrixSize - 1
                            )
                        }

                        if (canContinueBottomLeft) {
                            closedCells += matrix.calculateClosedCalls(row = leftBottomRow, col = leftBottomCol)

                            canContinueBottomLeft = matrix[leftBottomRow][leftBottomCol].canContinueCalculateCells(
                                additionalCondition = leftBottomRow != matrixSize - 1 && leftBottomCol != 0
                            )
                        }

                        matrix.printMatrix()

                        if (!canContinueTopLeft && !canContinueBottomRight && !canContinueTopRight && !canContinueBottomLeft) break
                    }
                }
            }
        }

        return matrixSize * matrixSize - closedCells
    }

    fun Array<CharArray>.printMatrix() {
        println("\narray:\n" + this.joinToString("\n") { charArray ->
            charArray.joinToString(", ")
        } + "\n")
    }

    private fun Array<CharArray>.calculateClosedCalls(row: Int, col: Int): Int {
        return if (this[row][col] == ChessPiece.EMPTY_CELL.value) {
            this[row][col] = ChessPiece.BLOCKED_CELL_SYMBOL.value
            1
        } else {
            0
        }
    }

    private fun Char.canContinueCalculateCells(additionalCondition: Boolean): Boolean {
        return this != ChessPiece.BISHOP.value && this != ChessPiece.ROOK.value && additionalCondition
    }
}

private enum class ChessPiece(val value: Char) {
    BISHOP(value = 'B'),
    ROOK(value = 'R'),
    EMPTY_CELL(value = '*'),
    BLOCKED_CELL_SYMBOL(value = '0')
}