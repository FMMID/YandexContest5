import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.abs
import kotlin.math.ceil

fun readDataFromFile(fileName: String): FormattingFileInputData {
    var firstLine: String? = null
    val otherLines = mutableListOf<String>()

    BufferedReader(FileReader(fileName)).use { reader ->
        firstLine = reader.readLine()
        var line = reader.readLine()
        while (line != null) {
            otherLines.add(line)
            line = reader.readLine()
        }
    }

    val (maxStrokeWidth, strokeHeight, charWidth) = firstLine!!.split(' ').map(String::toLong)
    val listOfDocElements = mutableListOf<FormattingDocument.DocElement>()

    otherLines.forEach { string ->
        val elements = Regex("""\b\w+\b|\([^()]*\)""").findAll(string).map { it.value }

        if (!elements.any()) {
            listOfDocElements.add(FormattingDocument.DocElement.NewParagraph)
        }

        elements.forEach { element ->
            val attrsRegex = Regex("""(\w+)=(\w+|-?\d+)""")
            val matches = attrsRegex.findAll(element)

            if (matches.none()) {
                listOfDocElements.add(FormattingDocument.DocElement.Symbols(element))
            } else {
                val parameters = matches.associate { it.groupValues[1] to it.groupValues[2] }
                val layout = parameters["layout"]
                val width = parameters["width"]?.toLong() ?: 0
                val height = parameters["height"]?.toLong() ?: 0

                when (layout) {
                    "floating" -> {
                        val dx = parameters["dx"]?.toLong() ?: 0
                        val dy = parameters["dy"]?.toLong() ?: 0
                        listOfDocElements.add(
                            FormattingDocument.DocElement.Image.FloatingImage(
                                width,
                                height,
                                dx,
                                dy
                            )
                        )
                    }

                    "surrounded" -> {
                        listOfDocElements.add(FormattingDocument.DocElement.Image.SurroundedImage(width, height))
                    }

                    "embedded" -> {
                        listOfDocElements.add(FormattingDocument.DocElement.Image.EmbeddedImage(width, height))
                    }
                }
            }
        }
    }

    return FormattingFileInputData(maxStrokeWidth, strokeHeight, charWidth, listOfDocElements)
}

data class FormattingFileInputData(
    val maxStrokeWidth: Long,
    val strokeHeight: Long,
    val charWidth: Long,
    val listOfDocElements: List<FormattingDocument.DocElement>
)

class FormattingDocument {

    private var currentY = 0L
    private var currentX = 0L
    private var currentFragment = 0
    private var currentFragmentHeight = 0L
    private var lastDocElement: DocElement = DocElement.NewParagraph

    fun getFormattingResult(
        maxStrokeWidth: Long,
        strokeHeight: Long,
        charWidth: Long,
        docElements: List<DocElement>
    ): FormattingResult {
        val imagesListCoordinates = mutableListOf<Coordinate>()
        val availableFragments = Array(1000) {
            Array(size = maxStrokeWidth.toInt() + 1) { Cell.FREE.value }
        }

        currentY = 0L
        currentX = 0L
        currentFragment = 0
        currentFragmentHeight = strokeHeight

        docElements.forEach { docElement ->
            when (docElement) {
                is DocElement.Symbols -> {
                    tryPlaceCurrentDocElement(
                        availableFragments = availableFragments,
                        neededSymbols = (docElement.value.length * charWidth).toInt(),
                        strokeHeight = strokeHeight,
                        isSupportSpace = true,
                        charWidth = charWidth,
                    ) { newX -> currentX = newX }
                }

                is DocElement.Image -> {
                    when (docElement) {
                        is DocElement.Image.EmbeddedImage -> {
                            when {
                                currentX == 0L -> {
                                    imagesListCoordinates.add(Coordinate(x = 0, y = currentY))
                                    currentX = docElement.width
                                }

                                currentX > 0L -> {
                                    tryPlaceCurrentDocElement(
                                        availableFragments = availableFragments,
                                        neededSymbols = docElement.width.toInt(),
                                        strokeHeight = strokeHeight,
                                        isSupportSpace = true,
                                        charWidth = charWidth,
                                    ) { newX ->
                                        currentX = newX

                                        imagesListCoordinates.add(
                                            Coordinate(
                                                x = currentX - docElement.width,
                                                y = currentY
                                            )
                                        )
                                    }
                                }
                            }

                            if (docElement.height > currentFragmentHeight) {
                                currentFragmentHeight = docElement.height
                            }
                        }

                        is DocElement.Image.FloatingImage -> {
                            var dxImageOffset = if (docElement.dx > 0) {
                                val rightPointOfImage = currentX + docElement.width
                                if (rightPointOfImage + docElement.dx > maxStrokeWidth) {
                                    abs(currentX - (rightPointOfImage - maxStrokeWidth))
                                } else {
                                    currentX + docElement.dx
                                }
                            } else {
                                if (currentX + docElement.dx < 0) {
                                    0
                                } else {
                                    currentX + docElement.dx
                                }
                            }
                            var dyImageOffset = currentY + docElement.dy

                            if (lastDocElement is DocElement.Image.FloatingImage) {
                                val floatingImage = lastDocElement as DocElement.Image.FloatingImage
                                val offsets = imagesListCoordinates.last()
                                dyImageOffset = offsets.y + floatingImage.dy
                                dxImageOffset = offsets.x + floatingImage.width
                            }

                            imagesListCoordinates.add(Coordinate(x = dxImageOffset, y = dyImageOffset))
                        }

                        is DocElement.Image.SurroundedImage -> {
                            var addedFromX = 0L

                            when {
                                currentX == 0L -> {
                                    imagesListCoordinates.add(Coordinate(x = 0, y = currentY))

                                    addedFromX = 0
                                    currentX = docElement.width
                                }

                                currentX > 0L -> {
                                    tryPlaceCurrentDocElement(
                                        availableFragments = availableFragments,
                                        neededSymbols = docElement.width.toInt(),
                                        strokeHeight = strokeHeight,
                                        isSupportSpace = false,
                                        charWidth = charWidth,
                                    ) { newX ->
                                        currentX = newX
                                        addedFromX = currentX - docElement.width

                                        imagesListCoordinates.add(
                                            Coordinate(
                                                x = addedFromX,
                                                y = currentY
                                            )
                                        )
                                    }
                                }
                            }
                            val modifiedStringsCount = ceil(docElement.height / strokeHeight.toFloat()).toInt()
//                            val modifiedStringsCount = (docElement.height / strokeHeight).toInt()
                            val modifiedFrom = addedFromX.toInt()
                            val modifiedTo = addedFromX.toInt() + docElement.width.toInt() + 1

                            for (index in currentFragment..<currentFragment + modifiedStringsCount) {
                                availableFragments[index].fill(
                                    element = Cell.BLOCKED.value,
                                    fromIndex = modifiedFrom,
                                    toIndex = modifiedTo
                                )
                            }
                        }
                    }
                }

                DocElement.NewParagraph -> {
                    currentX = 0L
                    currentFragment++
                    currentFragmentHeight = strokeHeight
                }
            }

            lastDocElement = docElement
        }

        return FormattingResult(imagesListCoordinates)
    }

    private fun tryPlaceCurrentDocElement(
        availableFragments: Array<Array<Char>>,
        neededSymbols: Int,
        strokeHeight: Long,
        isSupportSpace: Boolean,
        charWidth: Long,
        finishedPlaceDocElement: (Long) -> Unit
    ) {
        while (true) {
            val hasAvailableSymbols = hasAvailableSymbols(
                availableFragments = availableFragments,
                currentFragment = currentFragment,
                neededSymbols = neededSymbols,
                currentX = currentX,
                isSupportSpace = isSupportSpace,
                charWidth
            )

            if (hasAvailableSymbols.first) {
                finishedPlaceDocElement(hasAvailableSymbols.second.toLong())
                break
            } else {
                currentX = 0L
                currentY += currentFragmentHeight
                currentFragment++
                currentFragmentHeight = strokeHeight
            }
        }
    }

    fun goToNextFragment(strokeHeight: Long) {
        currentX = 0L
        currentY += currentFragmentHeight
        currentFragment++
        currentFragmentHeight = strokeHeight
    }

    private fun hasAvailableSymbols(
        availableFragments: Array<Array<Char>>,
        currentFragment: Int,
        neededSymbols: Int,
        currentX: Long,
        isSupportSpace: Boolean,
        charWidth: Long
    ): Pair<Boolean, Int> {
        var freeSymbols = 0
        var spacePixels = if (isSupportSpace && currentX > 0) {
            SPACE * charWidth.toInt()
        } else {
            0
        }

        for (index in currentX.toInt() + 1..<availableFragments[currentFragment].size) {
            if (availableFragments[currentFragment][index] == Cell.FREE.value) {
                freeSymbols++
                availableFragments[currentFragment][index] = Cell.BLOCKED.value
            } else {
                freeSymbols = 0
                spacePixels = 0
            }

            if (freeSymbols == neededSymbols + spacePixels) {
                return Pair(true, index)
            }
        }
        return Pair(false, 0)
    }

    enum class Cell(val value: Char) {
        FREE(value = '*'),
        BLOCKED(value = '0')
    }

    companion object {
        private const val SPACE = 1
    }

    sealed interface DocElement {

        data class Symbols(val value: String) : DocElement

        sealed class Image(val width: Long, val height: Long) : DocElement {

            class EmbeddedImage(width: Long, height: Long) : DocElement.Image(width, height)

            class SurroundedImage(width: Long, height: Long) : DocElement.Image(width, height)

            class FloatingImage(width: Long, height: Long, val dx: Long, val dy: Long) : DocElement.Image(width, height)
        }

        data object NewParagraph : DocElement
    }

    data class FormattingResult(private val listOfCoordinates: List<Coordinate>) {

        fun printResult(fileName: String) {
            val file = File(fileName)

            file.bufferedWriter().use { out ->
                listOfCoordinates.forEachIndexed { index, coordinate ->
                    out.write("${coordinate.x} ${coordinate.y}")
                    if (index < listOfCoordinates.size - 1) {
                        out.newLine()
                    }
                }
            }
        }
    }

    data class Coordinate(val x: Long, val y: Long)
}