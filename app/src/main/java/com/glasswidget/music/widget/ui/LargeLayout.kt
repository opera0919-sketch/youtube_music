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
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.glasswidget.music.domain.model.NowPlayingTrack

/** 5x3 / 5x4: album cover + title + artist + controls + progress bar (UI spec "Large"). */
@Composable
fun LargeLayout(track: NowPlayingTrack) {
    GlassPanel(backgroundArt = track.albumArtBackground) {
        Column(modifier = GlanceModifier.fillMaxSize().padding(14.dp)) {
            Row(
                modifier = GlanceModifier.fillMaxWidth().defaultWeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AlbumArtImage(bitmap = track.albumArt, size = 80.dp)
                Spacer(modifier = GlanceModifier.width(14.dp))
                Column(
                    modifier = GlanceModifier.fillMaxHeight().defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = track.title.ifBlank { "—" }, maxLines = 1, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(text = track.artist, maxLines = 1, style = TextStyle(fontWeight = FontWeight.Normal))
                    Text(text = track.album, maxLines = 1, style = TextStyle(fontWeight = FontWeight.Normal))
                }
            }
            Spacer(modifier = GlanceModifier.height(8.dp))
            ProgressSection(track)
            Spacer(modifier = GlanceModifier.height(8.dp))
            Row(modifier = GlanceModifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = GlanceModifier.defaultWeight())
                PlaybackControls(status = track.playbackStatus, showSkipButtons = true)
                Spacer(modifier = GlanceModifier.defaultWeight())
            }
        }
    }
}
