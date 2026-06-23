package com.glasswidget.music.domain.usecase

import com.glasswidget.music.domain.repository.NowPlayingRepository
import javax.inject.Inject

class SkipToNextUseCase @Inject constructor(
    private val repository: NowPlayingRepository
) {
    suspend operator fun invoke() = repository.skipToNext()
}
