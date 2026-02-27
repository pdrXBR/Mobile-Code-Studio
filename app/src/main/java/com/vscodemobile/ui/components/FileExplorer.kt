package com.vscodemobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vscodemobile.data.CodeFile
import com.vscodemobile.ui.theme.VSCodeColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.IconButton

@Composable
fun FileExplorer(
    files: List<CodeFile>,
    currentFile: CodeFile?,
    onFileClick: (CodeFile) -> Unit,
    onDeleteFile: (CodeFile) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(VSCodeColors.SidebarBackground)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "EXPLORER",
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 11.sp,
                color = VSCodeColors.EditorTextMuted
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        files.forEach { file ->
            FileItem(
                file = file,
                isActive = file.id == currentFile?.id,
                onFileClick = { onFileClick(file) },
                onDeleteFile = { onDeleteFile(file) }
            )
        }
    }
}

@Composable
fun FileItem(
    file: CodeFile,
    isActive: Boolean,
    onFileClick: () -> Unit,
    onDeleteFile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isActive) VSCodeColors.ItemActive else Color.Transparent
    val textColor = if (isActive) VSCodeColors.EditorText else VSCodeColors.EditorTextMuted

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(backgroundColor)
            .clickable(onClick = onFileClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Code,
                contentDescription = null,
                tint = VSCodeColors.AccentColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = file.name,
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    color = textColor
                ),
                modifier = Modifier.weight(1f)
            )
        }

        IconButton(
            onClick = onDeleteFile,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = VSCodeColors.ErrorColor,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
