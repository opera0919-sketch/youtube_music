package com.glasswidget.music.di

import com.glasswidget.music.domain.repository.NowPlayingRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Glance widgets and ActionCallbacks are instantiated outside the Hilt graph
 * (via reflection / no-arg constructors), so dependencies are pulled through
 * this entry point instead of constructor injection.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface NowPlayingEntryPoint {
    fun nowPlayingRepository(): NowPlayingRepository
}
