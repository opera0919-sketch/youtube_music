package com.glasswidget.music.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.defaultWeight
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.glasswidget.music.domain.model.NowPlayingTrack

/** 4x2 / 4x3: album cover + title + artist + controls (UI spec "Medium"). */
@Composable
fun MediumLayout(track: NowPlayingTrack) {
    GlassPanel(backgroundArt = track.albumArtBackground) {
        Row(
            modifier = GlanceModifier.fillMaxSize().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlbumArtImage(bitmap = track.albumArt, size = 64.dp)
            Spacer(modifier = GlanceModifier.width(12.dp))
            Column(
                modifier = GlanceModifier.fillMaxHeight().defaultWeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = track.title.ifBlank { "—" }, maxLines = 1, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = track.artist, maxLines = 1, style = TextStyle(fontWeight = FontWeight.Normal))
                PlaybackControls(status = track.playbackStatus, showSkipButtons = true)
            }
        }
    }
}
