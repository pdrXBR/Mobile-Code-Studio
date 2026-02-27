package com.vscodemobile.pythonsupport

/**
 * Analyzes Python code for errors and issues
 * Features:
 * - Parentheses matching
 * - Indentation validation
 * - Import validation (mock)
 * - Basic code structure analysis
 */
class PythonAnalyzer {
    
    data class AnalysisResult(
        val isValid: Boolean,
        val errors: List<AnalysisError>,
        val warnings: List<AnalysisWarning>,
        val structure: CodeStructure
    )
    
    data class AnalysisError(
        val message: String,
        val line: Int,
        val column: Int,
        val type: ErrorType,
        val fixSuggestion: String? = null
    )
    
    data class AnalysisWarning(
        val message: String,
        val line: Int,
        val severity: WarningSeverity
    )
    
    data class CodeStructure(
        val classes: List<ClassInfo>,
        val functions: List<FunctionInfo>,
        val imports: List<ImportInfo>
    )
    
    data class ClassInfo(
        val name: String,
        val line: Int,
        val methods: List<String>
    )
    
    data class FunctionInfo(
        val name: String,
        val line: Int,
        val parameters: List<String>,
        val hasDocstring: Boolean
    )
    
    data class ImportInfo(
        val module: String,
        val alias: String?,
        val line: Int,
        val isValid: Boolean
    )
    
    enum class ErrorType {
        SYNTAX_ERROR, INDENTATION_ERROR, NAME_ERROR, IMPORT_ERROR
    }
    
    enum class WarningSeverity {
        LOW, MEDIUM, HIGH
    }
    
    fun analyze(content: String): AnalysisResult {
        val lines = content.split("\n")
        val errors = mutableListOf<AnalysisError>()
        val warnings = mutableListOf<AnalysisWarning>()
        val classes = mutableListOf<ClassInfo>()
        val functions = mutableListOf<FunctionInfo>()
        val imports = mutableListOf<ImportInfo>()
        
        validateParentheses(lines, errors)
        validateIndentation(lines, errors)
        extractStructure(lines, classes, functions, imports, errors)
        
        val isValid = errors.isEmpty()
        
        return AnalysisResult(
            isValid = isValid,
            errors = errors,
            warnings = warnings,
            structure = CodeStructure(classes, functions, imports)
        )
    }
    
    private fun validateParentheses(lines: List<String>, errors: MutableList<AnalysisError>) {
        var parenBalance = 0
        var bracketBalance = 0
        var braceBalance = 0
        
        for ((lineIndex, line) in lines.withIndex()) {
            var inString = false
            var stringChar = ' '
            
            for ((charIndex, char) in line.withIndex()) {
                if (!inString && (char == '"' || char == '\'')) {
                    inString = true
                    stringChar = char
                } else if (inString && char == stringChar && (charIndex == 0 || line[charIndex - 1] != '\\')) {
                    inString = false
                }
                
                if (!inString) {
                    when (char) {
                        '(' -> parenBalance++
                        ')' -> {
                            parenBalance--
                            if (parenBalance < 0) {
                                errors.add(AnalysisError(
                                    message = "Unmatched closing parenthesis",
                                    line = lineIndex + 1,
                                    column = charIndex + 1,
                                    type = ErrorType.SYNTAX_ERROR,
                                    fixSuggestion = "Remove extra ')' or add matching '('"
                                ))
                                parenBalance = 0
                            }
                        }
                        '[' -> bracketBalance++
                        ']' -> {
                            bracketBalance--
                            if (bracketBalance < 0) {
                                errors.add(AnalysisError(
                                    message = "Unmatched closing bracket",
                                    line = lineIndex + 1,
                                    column = charIndex + 1,
                                    type = ErrorType.SYNTAX_ERROR
                                ))
                                bracketBalance = 0
                            }
                        }
                        '{' -> braceBalance++
                        '}' -> {
                            braceBalance--
                            if (braceBalance < 0) {
                                errors.add(AnalysisError(
                                    message = "Unmatched closing brace",
                                    line = lineIndex + 1,
                                    column = charIndex + 1,
                                    type = ErrorType.SYNTAX_ERROR
                                ))
                                braceBalance = 0
                            }
                        }
                    }
                }
            }
        }
        
        if (parenBalance > 0) {
            errors.add(AnalysisError(
                message = "Unclosed parenthesis",
                line = lines.size,
                column = 0,
                type = ErrorType.SYNTAX_ERROR,
                fixSuggestion = "Add $parenBalance closing ')'"
            ))
        }
    }
    
    private fun validateIndentation(lines: List<String>, errors: MutableList<AnalysisError>) {
        var expectedIndent = 0
        
        for ((lineIndex, line) in lines.withIndex()) {
            if (line.trim().isEmpty()) continue
            
            val currentIndent = line.takeWhile { it == ' ' || it == '\t' }.length
            val lineContent = line.trim()
            
            // Check if indentation is multiple of 4
            if (currentIndent % 4 != 0 && currentIndent > 0) {
                errors.add(AnalysisError(
                    message = "Indentation is not multiple of 4",
                    line = lineIndex + 1,
                    column = currentIndent + 1,
                    type = ErrorType.INDENTATION_ERROR,
                    fixSuggestion = "Use multiples of 4 spaces for indentation"
                ))
            }
            
            // Update expected indent for next line
            if (lineContent.endsWith(":")) {
                expectedIndent += 4
            } else if (lineContent.startsWith("else:") || lineContent.startsWith("elif ") ||
                       lineContent.startsWith("except") || lineContent.startsWith("finally:")) {
                expectedIndent = expectedIndent.coerceAtLeast(4) - 4 + 4
            }
        }
    }
    
    private fun extractStructure(
        lines: List<String>,
        classes: MutableList<ClassInfo>,
        functions: MutableList<FunctionInfo>,
        imports: MutableList<ImportInfo>,
        errors: MutableList<AnalysisError>
    ) {
        for ((lineIndex, line) in lines.withIndex()) {
            val trimmed = line.trim()
            
            // Extract classes
            if (trimmed.startsWith("class ")) {
                val className = trimmed.substringAfter("class ")
                    .substringBefore("(")
                    .substringBefore(":")
                    .trim()
                classes.add(ClassInfo(className, lineIndex + 1, emptyList()))
            }
            
            // Extract functions
            if (trimmed.startsWith("def ")) {
                val funcName = trimmed.substringAfter("def ")
                    .substringBefore("(")
                    .trim()
                val params = trimmed.substringAfter("(")
                    .substringBefore(")")
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                
                val hasDocstring = lineIndex + 1 < lines.size && 
                    (lines[lineIndex + 1].trim().startsWith("\"\"\"") || 
                     lines[lineIndex + 1].trim().startsWith("'''"))
                
                functions.add(FunctionInfo(funcName, lineIndex + 1, params, hasDocstring))
            }
            
            // Extract imports
            if (trimmed.startsWith("import ")) {
                val importPart = trimmed.substringAfter("import ").trim()
                val modules = importPart.split(",").map { it.trim() }
                for (module in modules) {
                    val isValid = validateImport(module)
                    imports.add(ImportInfo(module, null, lineIndex + 1, isValid))
                    if (!isValid) {
                        errors.add(AnalysisError(
                            message = "Invalid import format",
                            line = lineIndex + 1,
                            column = 1,
                            type = ErrorType.IMPORT_ERROR
                        ))
                    }
                }
            }
            
            if (trimmed.startsWith("from ")) {
                val parts = trimmed.split(" import ")
                if (parts.size == 2) {
                    val module = parts[0].substringAfter("from ").trim()
                    val imported = parts[1].trim()
                    imports.add(ImportInfo(module, imported, lineIndex + 1, true))
                }
            }
        }
    }
    
    private fun validateImport(module: String): Boolean {
        // Mock validation - just check if it's not empty and has valid characters
        return module.isNotEmpty() && module.matches(Regex("[a-zA-Z0-9_.]+"))
    }
}
