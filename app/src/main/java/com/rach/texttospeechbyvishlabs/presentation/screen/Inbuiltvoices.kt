package com.rach.texttospeechbyvishlabs.presentation.screen

import android.content.Intent
import android.speech.tts.TextToSpeech
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rach.texttospeechbyvishlabs.BackTopBar
import com.rach.texttospeechbyvishlabs.presentation.viewmodel.TtsViewModels
import kotlinx.coroutines.delay
import kotlin.collections.forEach
import kotlin.collections.groupBy
import kotlin.let

@Composable
fun InbuiltVoiceScreen(
    paddingValues: PaddingValues,
    viewModel: TtsViewModels,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Load voices after TTS is ready
    LaunchedEffect(Unit) {
        delay(500)
        viewModel.loadVoices()
    }

    // ✅ Only device-available (offline) voices
    val groupedVoices = remember(state.voices) {
        state.voices
            .filter { !it.isNetworkConnectionRequired }
            .groupBy { it.locale.displayName }
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
            if (groupedVoices.isEmpty()) {
                item {
                    NoVoicesInstalledCard {
                        val intent =
                            Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
                        context.startActivity(intent)
                    }
                }
            } else {
                groupedVoices.forEach { (language, voices) ->
                    item {
                        ExpandableLanguageItem(
                            language = language,
                            voices = voices,
                            onVoiceSelected = { voice ->
                                viewModel.selectVoice(voice)
                                viewModel.speak(
                                    "This is a preview of the selected voice"
                                )
                            }
                        )
                    }
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
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column {
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
                    imageVector = if (expanded)
                        Icons.Default.ExpandLess
                    else
                        Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

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
            text = "Offline • Installed",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}



@Composable
fun NoVoicesInstalledCard(
    onInstallClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "No voices installed",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Install text-to-speech voices from device settings.",
                style = MaterialTheme.typography.bodySmall
            )

            Button(
                onClick = onInstallClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Open TTS Settings")
            }
        }
    }
}

