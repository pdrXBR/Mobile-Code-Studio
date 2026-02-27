package com.vscodemobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vscodemobile.ui.theme.VSCodeColors

@Composable
fun NewFileDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var fileName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.9f)
                .background(VSCodeColors.SidebarBackground)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "New File",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 16.sp,
                            color = VSCodeColors.EditorText
                        )
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = VSCodeColors.EditorTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // File name input
                TextField(
                    value = fileName,
                    onValueChange = { fileName = it },
                    label = {
                        Text(
                            "File name",
                            style = TextStyle(fontSize = 12.sp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 14.sp,
                        color = VSCodeColors.EditorText
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = VSCodeColors.ItemActive,
                        unfocusedContainerColor = VSCodeColors.ItemHover,
                        focusedTextColor = VSCodeColors.EditorText,
                        unfocusedTextColor = VSCodeColors.EditorTextMuted,
                        focusedIndicatorColor = VSCodeColors.AccentColor,
                        unfocusedIndicatorColor = VSCodeColors.SidebarBorder
                    ),
                    singleLine = true
                )

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VSCodeColors.ItemHover,
                            contentColor = VSCodeColors.EditorText
                        )
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (fileName.isNotBlank()) {
                                onConfirm(fileName)
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VSCodeColors.AccentColor,
                            contentColor = VSCodeColors.EditorBackground
                        ),
                        enabled = fileName.isNotBlank()
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}
