package com.vscodemobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vscodemobile.data.CodeFile
import com.vscodemobile.ui.theme.VSCodeColors

@Composable
fun EditorTopBar(
    currentFile: CodeFile?,
    onNewFileClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(VSCodeColors.SidebarBackground)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // File name and language
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = currentFile?.name ?: "No file selected",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = VSCodeColors.EditorText
                )
            )
            Text(
                text = currentFile?.language?.uppercase() ?: "TEXT",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 11.sp,
                    color = VSCodeColors.EditorTextMuted
                )
            )
        }

        // Action buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNewFileClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "New file",
                    tint = VSCodeColors.AccentColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = onSaveClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Save file",
                    tint = VSCodeColors.AccentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
