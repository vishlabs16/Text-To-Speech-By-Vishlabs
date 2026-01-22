package com.rach.texttospeechbyvishlabs.presentation.screen

import android.speech.tts.Voice
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rach.texttospeechbyvishlabs.BackTopBar
import com.rach.texttospeechbyvishlabs.presentation.viewmodel.TtsViewModels

@Composable
fun InbuiltVoiceScreen(
    paddingValues: PaddingValues,
    viewModel: TtsViewModels,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Load voices AFTER TTS is ready
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(500)
        viewModel.loadVoices()
    }

    val groupedVoices = remember(state.voices) {
        state.voices.groupBy { it.locale.displayName }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        BackTopBar("Inbuilt Voices", onBackClick)

        LazyColumn(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedVoices.forEach { (language, voices) ->
                item {
                    ExpandableLanguageItem(
                        language = language,
                        voices = voices,
                        onVoiceSelected = { voice ->
                            viewModel.selectVoice(voice)
                            viewModel.speak("This is a preview of the selected voice")
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ExpandableLanguageItem(
    language: String,
    voices: List<Voice>,
    onVoiceSelected: (Voice) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Header (Language row)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = language,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${voices.size} voices",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            // Voice list
            if (expanded) {
                Divider()
                voices.forEach { voice ->
                    VoiceItem(
                        voice = voice,
                        onClick = { onVoiceSelected(voice) }
                    )
                }
            }
        }
    }
}

@Composable
fun VoiceItem(
    voice: Voice,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = voice.name,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = buildString {
                append(
                    if (voice.isNetworkConnectionRequired) "Online" else "Offline"
                )
                voice.features?.let {
                    if (it.contains("male")) append(" • Male")
                    if (it.contains("female")) append(" • Female")
                }
            },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
