package com.vscodemobile.pythonsupport

/**
 * Handles Python file recognition and metadata
 */
class PythonFileHandler {
    
    fun isPythonFile(fileName: String): Boolean {
        return fileName.endsWith(".py")
    }
    
    fun getPythonFileType(fileName: String): PythonFileType {
        return when {
            fileName.endsWith("_test.py") || fileName.endsWith("test_.py") -> PythonFileType.TEST
            fileName == "__init__.py" -> PythonFileType.PACKAGE_INIT
            fileName == "setup.py" -> PythonFileType.SETUP
            fileName == "__main__.py" -> PythonFileType.MAIN
            fileName.endsWith("manage.py") -> PythonFileType.DJANGO_MANAGE
            else -> PythonFileType.MODULE
        }
    }
    
    fun extractMetadata(content: String): PythonMetadata {
        val lines = content.split("\n")
        val imports = mutableListOf<String>()
        val classes = mutableListOf<String>()
        val functions = mutableListOf<String>()
        var docstring = ""
        
        var inDocstring = false
        var docstringStart = -1
        
        for ((index, line) in lines.withIndex()) {
            val trimmed = line.trim()
            
            // Check for docstrings
            if (trimmed.startsWith("\"\"\"") || trimmed.startsWith("'''")) {
                if (!inDocstring) {
                    inDocstring = true
                    docstringStart = index
                } else {
                    inDocstring = false
                    docstring = lines.subList(docstringStart, index + 1).joinToString("\n")
                }
            }
            
            // Don't process inside docstrings
            if (inDocstring) continue
            
            // Extract imports
            if (trimmed.startsWith("import ") || trimmed.startsWith("from ")) {
                imports.add(trimmed)
            }
            
            // Extract class definitions
            if (trimmed.startsWith("class ")) {
                val className = trimmed.substringAfter("class ")
                    .substringBefore("(")
                    .substringBefore(":")
                    .trim()
                classes.add(className)
            }
            
            // Extract function definitions
            if (trimmed.startsWith("def ")) {
                val functionName = trimmed.substringAfter("def ")
                    .substringBefore("(")
                    .trim()
                functions.add(functionName)
            }
        }
        
        return PythonMetadata(
            imports = imports,
            classes = classes,
            functions = functions,
            docstring = docstring
        )
    }
    
    enum class PythonFileType {
        MODULE, TEST, PACKAGE_INIT, SETUP, MAIN, DJANGO_MANAGE
    }
    
    data class PythonMetadata(
        val imports: List<String>,
        val classes: List<String>,
        val functions: List<String>,
        val docstring: String
    )
}
