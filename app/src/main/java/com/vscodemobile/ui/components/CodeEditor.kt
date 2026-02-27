package com.vscodemobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vscodemobile.ui.theme.VSCodeColors

@Composable
fun CodeEditor(
    content: String,
    onContentChange: (String) -> Unit,
    language: String = "text",
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VSCodeColors.EditorBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState)
                .verticalScroll(scrollState)
        ) {
            // Line numbers
            LineNumbers(
                lineCount = content.split("\n").size,
                modifier = Modifier
                    .background(VSCodeColors.EditorLineNumberBackground)
                    .padding(horizontal = 8.dp)
            )

            // Editor
            BasicTextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = VSCodeColors.EditorText,
                    lineHeight = 20.sp
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun LineNumbers(
    lineCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        repeat(lineCount) { index ->
            Text(
                text = (index + 1).toString(),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = VSCodeColors.EditorLineNumberText,
                    lineHeight = 20.sp
                ),
                modifier = Modifier
                    .height(20.dp)
                    .wrapContentHeight()
            )
        }
    }
}
