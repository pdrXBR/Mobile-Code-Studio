package com.vscodemobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vscodemobile.ui.components.CodeEditor
import com.vscodemobile.ui.components.EditorTopBar
import com.vscodemobile.ui.components.FileExplorer
import com.vscodemobile.ui.components.NewFileDialog
import com.vscodemobile.viewmodel.EditorViewModel

@Composable
fun VSCodeMobileApp(
    viewModel: EditorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val files by viewModel.files.collectAsState()
    val currentFile by viewModel.currentFile.collectAsState()
    val editorContent by viewModel.editorContent.collectAsState()
    
    var showNewFileDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Bar
        EditorTopBar(
            currentFile = currentFile,
            onNewFileClick = { showNewFileDialog = true },
            onSaveClick = { viewModel.saveCurrentFile() }
        )

        // Main Content
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // File Explorer
            FileExplorer(
                files = files,
                currentFile = currentFile,
                onFileClick = { viewModel.selectFile(it) },
                onDeleteFile = { viewModel.deleteFile(it) }
            )

            // Code Editor
            CodeEditor(
                content = editorContent,
                onContentChange = { viewModel.updateFileContent(it) },
                language = currentFile?.language ?: "text",
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // New File Dialog
    if (showNewFileDialog) {
        NewFileDialog(
            onDismiss = { showNewFileDialog = false },
            onConfirm = { fileName ->
                viewModel.createNewFile(fileName)
                showNewFileDialog = false
            }
        )
    }
}
