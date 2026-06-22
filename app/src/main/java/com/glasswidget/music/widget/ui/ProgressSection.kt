package com.glasswidget.music.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.LinearProgressIndicator
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.glasswidget.music.domain.model.NowPlayingTrack
import com.glasswidget.music.domain.model.PlaybackStatus
import com.glasswidget.music.widget.util.TimeFormatter
import kotlinx.coroutines.delay

/** FR-004: 1-second progress refresh, interpolated from the last known position. */
@Composable
fun ProgressSection(track: NowPlayingTrack) {
    val elapsedMs by produceState(initialValue = track.positionMs, track) {
        if (track.playbackStatus != PlaybackStatus.PLAYING) {
            value = track.positionMs
            return@produceState
        }
        while (true) {
            val elapsedSincePlaybackEvent =
                ((System.currentTimeMillis() - track.lastPositionUpdateTime) * track.playbackSpeed).toLong()
            val cap = track.durationMs.takeIf { it > 0 } ?: Long.MAX_VALUE
            value = (track.positionMs + elapsedSincePlaybackEvent).coerceIn(0, cap)
            delay(1_000)
        }
    }

    val progress = if (track.durationMs > 0) {
        (elapsedMs.toFloat() / track.durationMs.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    Column(modifier = GlanceModifier.fillMaxWidth()) {
        LinearProgressIndicator(
            modifier = GlanceModifier.fillMaxWidth(),
            progress = progress,
            color = ColorProvider(Color.White),
            backgroundColor = ColorProvider(Color.White.copy(alpha = 0.25f))
        )
        Row(modifier = GlanceModifier.fillMaxWidth()) {
            Text(
                text = "${TimeFormatter.format(elapsedMs)} / ${TimeFormatter.format(track.durationMs)}",
                style = TextStyle(color = ColorProvider(Color.White.copy(alpha = 0.85f)), fontWeight = FontWeight.Normal)
            )
        }
    }
}
