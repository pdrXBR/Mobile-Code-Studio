package com.vscodemobile.pythonsupport

import androidx.compose.ui.graphics.Color
import com.vscodemobile.ui.theme.VSCodeColors

/**
 * Basic Python syntax highlighting
 * Recognizes keywords and basic Python syntax
 */
class PythonSyntaxHighlighter {
    
    companion object {
        private val PYTHON_KEYWORDS = setOf(
            "def", "class", "import", "from", "return", "if", "else", "elif",
            "try", "except", "finally", "with", "for", "while", "break", "continue",
            "pass", "raise", "assert", "yield", "lambda", "async", "await",
            "in", "is", "not", "and", "or", "None", "True", "False",
            "as", "del", "global", "nonlocal", "print"
        )
        
        private val BUILTIN_FUNCTIONS = setOf(
            "len", "range", "enumerate", "zip", "map", "filter", "sorted",
            "list", "dict", "set", "tuple", "str", "int", "float", "bool",
            "open", "input", "__import__", "type", "isinstance", "issubclass"
        )
    }
    
    data class HighlightedToken(
        val text: String,
        val color: Color,
        val startIndex: Int,
        val endIndex: Int
    )
    
    data class SyntaxError(
        val message: String,
        val line: Int,
        val column: Int,
        val type: ErrorType
    )
    
    enum class ErrorType {
        SYNTAX_ERROR, INDENTATION_ERROR, NAME_ERROR, TYPE_ERROR
    }
    
    fun highlightLine(line: String, lineNumber: Int): List<HighlightedToken> {
        val tokens = mutableListOf<HighlightedToken>()
        var i = 0
        
        while (i < line.length) {
            // Skip whitespace
            if (line[i].isWhitespace()) {
                i++
                continue
            }
            
            // Handle strings
            if (line[i] == '"' || line[i] == '\'') {
                val quote = line[i]
                val start = i
                i++
                while (i < line.length && line[i] != quote) {
                    if (line[i] == '\\') i++
                    i++
                }
                if (i < line.length) i++
                tokens.add(HighlightedToken(
                    text = line.substring(start, i),
                    color = VSCodeColors.SyntaxString,
                    startIndex = start,
                    endIndex = i
                ))
                continue
            }
            
            // Handle comments
            if (line[i] == '#') {
                val start = i
                tokens.add(HighlightedToken(
                    text = line.substring(start),
                    color = VSCodeColors.SyntaxComment,
                    startIndex = start,
                    endIndex = line.length
                ))
                break
            }
            
            // Handle identifiers and keywords
            if (line[i].isLetter() || line[i] == '_') {
                val start = i
                while (i < line.length && (line[i].isLetterOrDigit() || line[i] == '_')) {
                    i++
                }
                val word = line.substring(start, i)
                
                val color = when {
                    word in PYTHON_KEYWORDS -> VSCodeColors.SyntaxKeyword
                    word in BUILTIN_FUNCTIONS -> VSCodeColors.SyntaxFunction
                    else -> VSCodeColors.SyntaxVariable
                }
                
                tokens.add(HighlightedToken(
                    text = word,
                    color = color,
                    startIndex = start,
                    endIndex = i
                ))
                continue
            }
            
            // Handle numbers
            if (line[i].isDigit()) {
                val start = i
                while (i < line.length && (line[i].isDigit() || line[i] == '.')) {
                    i++
                }
                tokens.add(HighlightedToken(
                    text = line.substring(start, i),
                    color = VSCodeColors.SyntaxNumber,
                    startIndex = start,
                    endIndex = i
                ))
                continue
            }
            
            // Handle operators
            if (isOperator(line[i])) {
                val start = i
                if (i + 1 < line.length && isOperator(line[i + 1])) {
                    i += 2
                } else {
                    i++
                }
                tokens.add(HighlightedToken(
                    text = line.substring(start, i),
                    color = VSCodeColors.SyntaxOperator,
                    startIndex = start,
                    endIndex = i
                ))
                continue
            }
            
            i++
        }
        
        return tokens
    }
    
    fun analyzeForErrors(content: String): List<SyntaxError> {
        val errors = mutableListOf<SyntaxError>()
        val lines = content.split("\n")
        var openParens = 0
        var openBrackets = 0
        var openBraces = 0
        var prevIndentation = 0
        
        for ((lineIndex, line) in lines.withIndex()) {
            // Check parentheses balance
            for (char in line) {
                when (char) {
                    '(' -> openParens++
                    ')' -> {
                        openParens--
                        if (openParens < 0) {
                            errors.add(SyntaxError(
                                message = "Unmatched closing parenthesis",
                                line = lineIndex + 1,
                                column = line.indexOf(char) + 1,
                                type = ErrorType.SYNTAX_ERROR
                            ))
                            openParens = 0
                        }
                    }
                    '[' -> openBrackets++
                    ']' -> {
                        openBrackets--
                        if (openBrackets < 0) {
                            errors.add(SyntaxError(
                                message = "Unmatched closing bracket",
                                line = lineIndex + 1,
                                column = line.indexOf(char) + 1,
                                type = ErrorType.SYNTAX_ERROR
                            ))
                            openBrackets = 0
                        }
                    }
                    '{' -> openBraces++
                    '}' -> {
                        openBraces--
                        if (openBraces < 0) {
                            errors.add(SyntaxError(
                                message = "Unmatched closing brace",
                                line = lineIndex + 1,
                                column = line.indexOf(char) + 1,
                                type = ErrorType.SYNTAX_ERROR
                            ))
                            openBraces = 0
                        }
                    }
                }
            }
            
            // Check indentation
            if (line.trim().isNotEmpty()) {
                val currentIndentation = line.takeWhile { it.isWhitespace() }.length
                if (line.trim().startsWith("def ") || line.trim().startsWith("class ") ||
                    line.trim().endsWith(":")) {
                    prevIndentation = currentIndentation
                } else if (currentIndentation > prevIndentation + 4 && prevIndentation > 0) {
                    errors.add(SyntaxError(
                        message = "Indentation error: unexpected indent",
                        line = lineIndex + 1,
                        column = 1,
                        type = ErrorType.INDENTATION_ERROR
                    ))
                }
            }
        }
        
        // Check unclosed parentheses at EOF
        if (openParens > 0) {
            errors.add(SyntaxError(
                message = "Unclosed parenthesis",
                line = lines.size,
                column = 0,
                type = ErrorType.SYNTAX_ERROR
            ))
        }
        
        return errors
    }
    
    private fun isOperator(char: Char): Boolean {
        return char in "+-*/%=<>!&|^~"
    }
}
