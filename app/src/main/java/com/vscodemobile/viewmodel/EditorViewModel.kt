package com.vscodemobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vscodemobile.data.CodeFile
import com.vscodemobile.data.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Empty)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _files = MutableStateFlow<List<CodeFile>>(emptyList())
    val files: StateFlow<List<CodeFile>> = _files.asStateFlow()

    private val _currentFile = MutableStateFlow<CodeFile?>(null)
    val currentFile: StateFlow<CodeFile?> = _currentFile.asStateFlow()

    private val _editorContent = MutableStateFlow("")
    val editorContent: StateFlow<String> = _editorContent.asStateFlow()

    init {
        loadMockFiles()
    }

    private fun loadMockFiles() {
        viewModelScope.launch {
            try {
                _uiState.value = UIState.Loading
                
                // Mock files for demo
                val mockFiles = listOf(
                    CodeFile(
                        id = "1",
                        name = "main.py",
                        content = "# Main Python file\nprint('Hello VSCodeMobile')\n",
                        language = "python"
                    ),
                    CodeFile(
                        id = "2",
                        name = "app.js",
                        content = "// JavaScript file\nconsole.log('Hello');\n",
                        language = "javascript"
                    ),
                    CodeFile(
                        id = "3",
                        name = "index.html",
                        content = "<!DOCTYPE html>\n<html>\n</html>\n",
                        language = "html"
                    )
                )
                
                _files.value = mockFiles
                _currentFile.value = mockFiles.first()
                _editorContent.value = mockFiles.first().content
                _uiState.value = UIState.Success(mockFiles, mockFiles.first())
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun createNewFile(fileName: String) {
        viewModelScope.launch {
            try {
                val language = CodeFile.fromFileName(fileName)
                val newFile = CodeFile(
                    id = System.currentTimeMillis().toString(),
                    name = fileName,
                    content = "",
                    language = language
                )
                _files.value = _files.value + newFile
                selectFile(newFile)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error creating file")
            }
        }
    }

    fun selectFile(file: CodeFile) {
        _currentFile.value = file
        _editorContent.value = file.content
    }

    fun updateFileContent(content: String) {
        _editorContent.value = content
        _currentFile.value?.let { current ->
            val updated = current.copy(content = content)
            _currentFile.value = updated
            _files.value = _files.value.map {
                if (it.id == updated.id) updated else it
            }
        }
    }

    fun saveCurrentFile() {
        viewModelScope.launch {
            try {
                _currentFile.value?.let { file ->
                    val updated = file.copy(
                        content = _editorContent.value,
                        lastModified = System.currentTimeMillis()
                    )
                    _currentFile.value = updated
                    _files.value = _files.value.map {
                        if (it.id == updated.id) updated else it
                    }
                    _uiState.value = UIState.Success(_files.value, updated)
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Error saving file: ${e.message}")
            }
        }
    }

    fun deleteFile(file: CodeFile) {
        viewModelScope.launch {
            try {
                _files.value = _files.value.filter { it.id != file.id }
                if (_currentFile.value?.id == file.id) {
                    _currentFile.value = _files.value.firstOrNull()
                    _editorContent.value = _currentFile.value?.content ?: ""
                }
                _uiState.value = UIState.Success(_files.value, _currentFile.value)
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error deleting file")
            }
        }
    }
}
