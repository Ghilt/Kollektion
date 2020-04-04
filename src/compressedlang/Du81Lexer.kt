package compressedlang

import toGroupedStringList
import java.nio.file.Files
import java.nio.file.Paths

class Du81Lexer(private val filePath: String) {

    val source: String
    val tokens: List<ParsedElement>

    init {
        val stream = Files.newInputStream(Paths.get(filePath))
        var input = ""
        stream.buffered().reader().use { reader ->
            input = reader.readText()
        }
        source = input

        val sourceElements: List<ParsedElement> = input
            .toList()
            .toGroupedStringList(true) { x -> x == '"' }
            .toGroupedStringList { x -> x.toIntOrNull() != null }
            .map { it.toParsedElement() }

        tokens = sourceElements
    }

    fun printDiagnostics() {
        println(filePath)
        println(source)
    }
}

private fun String.toParsedElement(): ParsedElement {
    return when {
        this[0].isDigit() && this.contains('.') -> ParsedNumber(this.toDouble())
        this[0].isDigit() -> ParsedNumber(this.toInt())
        this[0] == '"' -> ParsedStringLiteral(this)
        else -> FunctionToken(this[0])
    }
}