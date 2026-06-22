package com.glasswidget.music.data.repository

import com.glasswidget.music.data.mediasession.NowPlayingDataSource
import com.glasswidget.music.domain.model.NowPlayingTrack
import com.glasswidget.music.domain.model.PlaybackStatus
import com.glasswidget.music.domain.repository.NowPlayingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NowPlayingRepositoryImpl @Inject constructor(
    private val dataSource: NowPlayingDataSource
) : NowPlayingRepository {

    override fun observeNowPlaying(): Flow<NowPlayingTrack?> = dataSource.nowPlaying

    override fun observeIsSessionActive(): Flow<Boolean> = dataSource.isSessionActive

    override suspend fun togglePlayPause() {
        val isPlaying = dataSource.nowPlaying.value?.playbackStatus == PlaybackStatus.PLAYING
        if (isPlaying) dataSource.transportControls?.pause() else dataSource.transportControls?.play()
    }

    override suspend fun skipToNext() {
        dataSource.transportControls?.skipToNext()
    }

    override suspend fun skipToPrevious() {
        dataSource.transportControls?.skipToPrevious()
    }

    override suspend fun seekTo(positionMs: Long) {
        dataSource.transportControls?.seekTo(positionMs)
    }
}
