package com.glasswidget.music.domain.usecase

import com.glasswidget.music.domain.model.NowPlayingTrack
import com.glasswidget.music.domain.repository.NowPlayingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNowPlayingUseCase @Inject constructor(
    private val repository: NowPlayingRepository
) {
    operator fun invoke(): Flow<NowPlayingTrack?> = repository.observeNowPlaying()
}
