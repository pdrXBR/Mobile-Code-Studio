package com.vscodemobile.data

import java.io.Serializable

data class CodeFile(
    val id: String,
    val name: String,
    val content: String,
    val language: String = "text",
    val lastModified: Long = System.currentTimeMillis()
) : Serializable {
    companion object {
        fun fromFileName(name: String): String {
            return when {
                name.endsWith(".py") -> "python"
                name.endsWith(".js") || name.endsWith(".jsx") -> "javascript"
                name.endsWith(".html") -> "html"
                name.endsWith(".kt") || name.endsWith(".java") -> "kotlin"
                name.endsWith(".json") -> "json"
                name.endsWith(".xml") -> "xml"
                name.endsWith(".css") -> "css"
                name.endsWith(".ts") || name.endsWith(".tsx") -> "typescript"
                else -> "text"
            }
        }
    }
}

sealed class UIState {
    object Loading : UIState()
    object Empty : UIState()
    data class Success(val files: List<CodeFile>, val currentFile: CodeFile? = null) : UIState()
    data class Error(val message: String) : UIState()
}
