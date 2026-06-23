package com.glasswidget.music.presentation.main

import com.glasswidget.music.domain.model.NowPlayingTrack

data class MainUiState(
    val isNotificationAccessGranted: Boolean = false,
    val nowPlaying: NowPlayingTrack? = null
)
