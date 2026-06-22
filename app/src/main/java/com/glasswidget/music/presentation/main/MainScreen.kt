package com.glasswidget.music.presentation.main

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.glasswidget.music.R
import com.glasswidget.music.domain.model.PlaybackStatus

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = stringResource(R.string.main_title), style = MaterialTheme.typography.headlineSmall)
            Text(
                text = stringResource(R.string.main_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (uiState.isNotificationAccessGranted) {
                            stringResource(R.string.main_permission_granted)
                        } else {
                            stringResource(R.string.main_permission_denied)
                        },
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = stringResource(R.string.main_permission_description), style = MaterialTheme.typography.bodySmall)
                    if (!uiState.isNotificationAccessGranted) {
                        Button(onClick = {
                            context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                        }) {
                            Text(text = stringResource(R.string.main_open_settings))
                        }
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = stringResource(R.string.main_now_playing_header), style = MaterialTheme.typography.titleMedium)
                    val track = uiState.nowPlaying
                    if (track == null || track.title.isBlank()) {
                        Text(text = stringResource(R.string.main_nothing_playing), style = MaterialTheme.typography.bodyMedium)
                    } else {
                        Text(text = track.title, style = MaterialTheme.typography.bodyLarge)
                        Text(text = track.artist, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = if (track.playbackStatus == PlaybackStatus.PLAYING) "▶ Playing" else "⏸ Paused",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.main_add_widget_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
