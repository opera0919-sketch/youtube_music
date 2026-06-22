package com.glasswidget.music.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.glasswidget.music.domain.model.NowPlayingTrack

/** 2x2: album cover + title + play button (UI spec "Small"). */
@Composable
fun SmallLayout(track: NowPlayingTrack) {
    GlassPanel(backgroundArt = track.albumArtBackground, isOnLightArtwork = track.isOnLightArtwork) {
        Column(
            modifier = GlanceModifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlbumArtImage(bitmap = track.albumArt, size = 48.dp)
            Text(
                text = track.title.ifBlank { "—" },
                maxLines = 1,
                style = TextStyle(color = ColorProvider(Color.White), fontWeight = FontWeight.Bold)
            )
            PlaybackControls(status = track.playbackStatus, showSkipButtons = false)
        }
    }
}
