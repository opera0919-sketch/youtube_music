package com.glasswidget.music.domain.model

import android.graphics.Bitmap
import android.os.SystemClock

data class NowPlayingTrack(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val albumArt: Bitmap? = null,
    val albumArtBackground: Bitmap? = null,
    val isOnLightArtwork: Boolean = false,
    val durationMs: Long = 0L,
    val positionMs: Long = 0L,
    val playbackSpeed: Float = 1f,
    // On the SystemClock.elapsedRealtime() clock, matching
    // PlaybackState.lastPositionUpdateTime.
    val lastPositionUpdateTime: Long = SystemClock.elapsedRealtime(),
    val playbackStatus: PlaybackStatus = PlaybackStatus.NONE
)
