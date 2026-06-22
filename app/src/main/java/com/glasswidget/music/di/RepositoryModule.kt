package com.glasswidget.music.di

import com.glasswidget.music.data.repository.NowPlayingRepositoryImpl
import com.glasswidget.music.domain.repository.NowPlayingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNowPlayingRepository(impl: NowPlayingRepositoryImpl): NowPlayingRepository
}
