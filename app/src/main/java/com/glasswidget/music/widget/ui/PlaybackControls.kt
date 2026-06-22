package com.glasswidget.music.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.size
import androidx.glance.layout.width
import com.glasswidget.music.R
import com.glasswidget.music.domain.model.PlaybackStatus
import com.glasswidget.music.widget.action.PlayPauseActionCallback
import com.glasswidget.music.widget.action.SkipNextActionCallback
import com.glasswidget.music.widget.action.SkipPreviousActionCallback

@Composable
fun PlaybackControls(status: PlaybackStatus, showSkipButtons: Boolean = true) {
    Row {
        if (showSkipButtons) {
            Image(
                provider = ImageProvider(R.drawable.ic_skip_previous),
                contentDescription = "Previous",
                modifier = GlanceModifier
                    .size(32.dp)
                    .clickable(actionRunCallback<SkipPreviousActionCallback>())
            )
            Spacer(modifier = GlanceModifier.width(12.dp))
        }
        Image(
            provider = ImageProvider(
                if (status == PlaybackStatus.PLAYING) R.drawable.ic_pause else R.drawable.ic_play
            ),
            contentDescription = "Play or pause",
            modifier = GlanceModifier
                .size(40.dp)
                .clickable(actionRunCallback<PlayPauseActionCallback>())
        )
        if (showSkipButtons) {
            Spacer(modifier = GlanceModifier.width(12.dp))
            Image(
                provider = ImageProvider(R.drawable.ic_skip_next),
                contentDescription = "Next",
                modifier = GlanceModifier
                    .size(32.dp)
                    .clickable(actionRunCallback<SkipNextActionCallback>())
            )
        }
    }
}
