package com.rach.texttospeechbyvishlabs.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rach.texttospeechbyvishlabs.BackTopBar


@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNavigateToLanguage: () -> Unit,
    onNavigateToVoice: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        BackTopBar(
            title = "Settings",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("App Settings", style = MaterialTheme.typography.titleMedium)

            ElevatedButton(onClick = onNavigateToLanguage, modifier = Modifier.fillMaxWidth()) {
                Text("Language Settings")
            }

            ElevatedButton(onClick = onNavigateToVoice, modifier = Modifier.fillMaxWidth()) {
                Text("Voice Category")
            }


            Text("App Info", style = MaterialTheme.typography.titleMedium)

            ElevatedButton(onClick = onNavigateToAbout,modifier = Modifier.fillMaxWidth()) {
                Text("About Us")
            }

            ElevatedButton(onClick = onNavigateToPrivacy, modifier = Modifier.fillMaxWidth()) {
                Text("Privacy Policy")
            }
        }
    }
}
