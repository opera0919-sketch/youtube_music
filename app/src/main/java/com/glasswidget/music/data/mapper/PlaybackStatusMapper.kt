package com.glasswidget.music.data.mapper

import android.media.session.PlaybackState
import com.glasswidget.music.domain.model.PlaybackStatus

fun mapPlaybackStateToStatus(state: Int?): PlaybackStatus = when (state) {
    null -> PlaybackStatus.NONE
    PlaybackState.STATE_PLAYING, PlaybackState.STATE_BUFFERING -> PlaybackStatus.PLAYING
    PlaybackState.STATE_PAUSED -> PlaybackStatus.PAUSED
    PlaybackState.STATE_STOPPED, PlaybackState.STATE_NONE, PlaybackState.STATE_ERROR -> PlaybackStatus.STOPPED
    else -> PlaybackStatus.STOPPED
}
