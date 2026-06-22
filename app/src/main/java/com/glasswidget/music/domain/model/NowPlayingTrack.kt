package com.glasswidget.music.domain.model

import android.graphics.Bitmap

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
    val lastPositionUpdateTime: Long = System.currentTimeMillis(),
    val playbackStatus: PlaybackStatus = PlaybackStatus.NONE
)
