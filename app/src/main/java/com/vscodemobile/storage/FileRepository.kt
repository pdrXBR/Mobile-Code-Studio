package com.vscodemobile.storage

import android.content.Context
import com.vscodemobile.data.CodeFile
import java.io.File

/**
 * Handles file persistence and retrieval
 */
class FileRepository(private val context: Context) {
    
    private val filesDir = File(context.filesDir, "code_files")
    
    init {
        if (!filesDir.exists()) {
            filesDir.mkdirs()
        }
    }
    
    fun saveFile(file: CodeFile): Boolean {
        return try {
            val codeFile = File(filesDir, "${file.id}_${file.name}")
            codeFile.writeText(file.content)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun loadFile(fileId: String, fileName: String): CodeFile? {
        return try {
            val file = File(filesDir, "${fileId}_${fileName}")
            if (file.exists()) {
                val content = file.readText()
                CodeFile(
                    id = fileId,
                    name = fileName,
                    content = content,
                    language = CodeFile.fromFileName(fileName)
                )
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun deleteFile(fileId: String, fileName: String): Boolean {
        return try {
            val file = File(filesDir, "${fileId}_${fileName}")
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun getAllFiles(): List<CodeFile> {
        return try {
            filesDir.listFiles()?.mapNotNull { file ->
                val parts = file.name.split("_", limit = 2)
                if (parts.size == 2) {
                    val fileId = parts[0]
                    val fileName = parts[1]
                    CodeFile(
                        id = fileId,
                        name = fileName,
                        content = file.readText(),
                        language = CodeFile.fromFileName(fileName)
                    )
                } else {
                    null
                }
            } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    fun clearAllFiles(): Boolean {
        return try {
            filesDir.deleteRecursively()
            filesDir.mkdirs()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
