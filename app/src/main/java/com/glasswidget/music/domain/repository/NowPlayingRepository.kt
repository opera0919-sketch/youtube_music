package com.glasswidget.music.domain.repository

import com.glasswidget.music.domain.model.NowPlayingTrack
import kotlinx.coroutines.flow.Flow

interface NowPlayingRepository {
    fun observeNowPlaying(): Flow<NowPlayingTrack?>
    fun observeIsSessionActive(): Flow<Boolean>
    suspend fun togglePlayPause()
    suspend fun skipToNext()
    suspend fun skipToPrevious()
    suspend fun seekTo(positionMs: Long)
}
