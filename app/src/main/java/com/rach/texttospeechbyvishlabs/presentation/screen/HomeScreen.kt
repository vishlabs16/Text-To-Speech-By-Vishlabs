package com.rach.texttospeechbyvishlabs.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.rach.texttospeechbyvishlabs.BannerAdView

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    text: String,
    onTextChange: (String) -> Unit,
    speakingIndex: Int,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        var showDialog by remember { mutableStateOf(false) }

        val wordCount = remember(text) {
            text.trim()
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() }
                .size
        }

        BannerAdView()

        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Enter your text", fontWeight = FontWeight.SemiBold)
                    Text("$wordCount / 2000", color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(8.dp))


                var fieldValue by remember {
                    mutableStateOf(TextFieldValue(text))
                }

                LaunchedEffect(text) {
                    if (text != fieldValue.text) {
                        fieldValue = fieldValue.copy(text = text)
                    }
                }

                val lines = text.split("\n")

                val speakableLineIndexes = remember(text) {
                    lines.mapIndexedNotNull { index, line ->
                        if (line.trim().isNotEmpty()) index else null
                    }
                }

                val highlightColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)

                val annotatedText = remember(text, speakingIndex) {
                    buildAnnotatedString {
                        lines.forEachIndexed { lineIndex, line ->
                            if (lineIndex == speakingIndex) {
                                withStyle(SpanStyle(background = highlightColor)) {
                                    append(line)
                                }
                            } else {
                                append(line)
                            }
                            if (lineIndex != lines.lastIndex) append("\n")
                        }
                    }
                }



                LaunchedEffect(annotatedText) {
                    fieldValue = fieldValue.copy(
                        annotatedString = annotatedText,
                        selection = fieldValue.selection
                    )
                }

                OutlinedTextField(
                    value = fieldValue,
                    onValueChange = { newValue ->
                        fieldValue = newValue
                        onTextChange(newValue.text)
                    },
                    modifier = Modifier.fillMaxSize(),
                    placeholder = { Text("Please enter the text to read aloud.") },
                    shape = RoundedCornerShape(16.dp),
                    maxLines = Int.MAX_VALUE
                )


            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Subscription Required") },
                text = { Text("You exceeded 2000 words") },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = onPrevious) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous"
                    )
                }

                IconButton(
                    onClick = {
                        isPlaying != isPlaying
                        onPlayPause()
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                IconButton(onClick = onNext) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next"
                    )
                }
            }
        }


    }
}
