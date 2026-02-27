package com.vscodemobile.pythonsupport

/**
 * Interface para conectar com Language Servers externos
 * Preparação para Pyright, pylsp ou server custom via Termux
 */
interface LanguageServerBridge {
    fun connect(serverHost: String, serverPort: Int): Boolean
    fun disconnect()
    
    fun getCompletion(file: String, line: Int, column: Int): List<CompletionItem>
    fun getHover(file: String, line: Int, column: Int): HoverInfo?
    fun getDefinition(file: String, line: Int, column: Int): DefinitionLocation?
    fun getDiagnostics(file: String, content: String): List<Diagnostic>
    
    data class CompletionItem(
        val label: String,
        val detail: String,
        val kind: String
    )
    
    data class HoverInfo(
        val content: String,
        val range: String
    )
    
    data class DefinitionLocation(
        val file: String,
        val line: Int,
        val column: Int
    )
    
    data class Diagnostic(
        val message: String,
        val line: Int,
        val column: Int,
        val severity: DiagnosticSeverity
    )
    
    enum class DiagnosticSeverity {
        INFO, WARNING, ERROR
    }
}
