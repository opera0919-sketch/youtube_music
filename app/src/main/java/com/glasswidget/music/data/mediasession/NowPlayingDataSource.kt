package com.glasswidget.music.data.mediasession

import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.PlaybackState
import com.glasswidget.music.core.ApplicationScope
import com.glasswidget.music.data.album.AlbumArtResolver
import com.glasswidget.music.data.mapper.mapPlaybackStateToStatus
import com.glasswidget.music.domain.model.NowPlayingTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of truth for "what YouTube Music is currently doing", fed by
 * [com.glasswidget.music.service.YouTubeMusicListenerService] via the
 * framework MediaController callbacks.
 */
@Singleton
class NowPlayingDataSource @Inject constructor(
    @ApplicationScope private val scope: CoroutineScope,
    private val albumArtResolver: AlbumArtResolver
) {

    private val _nowPlaying = MutableStateFlow<NowPlayingTrack?>(null)
    val nowPlaying: StateFlow<NowPlayingTrack?> = _nowPlaying.asStateFlow()

    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    @Volatile
    var transportControls: MediaController.TransportControls? = null
        private set

    private var albumArtJob: Job? = null

    fun attach(controller: MediaController) {
        transportControls = controller.transportControls
        _isSessionActive.value = true
        onMetadataChanged(controller.metadata)
        onPlaybackStateChanged(controller.playbackState)
    }

    fun detach() {
        albumArtJob?.cancel()
        transportControls = null
        _isSessionActive.value = false
        _nowPlaying.value = null
    }

    fun onMetadataChanged(metadata: MediaMetadata?) {
        val base = _nowPlaying.value ?: NowPlayingTrack()
        _nowPlaying.value = base.copy(
            title = metadata?.getString(MediaMetadata.METADATA_KEY_TITLE).orEmpty(),
            artist = metadata?.getString(MediaMetadata.METADATA_KEY_ARTIST).orEmpty(),
            album = metadata?.getString(MediaMetadata.METADATA_KEY_ALBUM).orEmpty(),
            durationMs = metadata?.getLong(MediaMetadata.METADATA_KEY_DURATION) ?: 0L
        )

        albumArtJob?.cancel()
        albumArtJob = scope.launch {
            val embedded = metadata?.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
            val artUri = metadata?.getString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI)
            val resolved = albumArtResolver.resolve(embedded, artUri)
            _nowPlaying.value = _nowPlaying.value?.copy(
                albumArt = resolved.foreground,
                albumArtBackground = resolved.blurredBackground,
                isOnLightArtwork = resolved.isLightArtwork
            )
        }
    }

    fun onPlaybackStateChanged(state: PlaybackState?) {
        val current = _nowPlaying.value ?: return
        _nowPlaying.value = current.copy(
            playbackStatus = mapPlaybackStateToStatus(state?.state),
            positionMs = state?.position ?: current.positionMs,
            playbackSpeed = state?.playbackSpeed ?: current.playbackSpeed,
            lastPositionUpdateTime = state?.lastPositionUpdateTime ?: System.currentTimeMillis()
        )
    }
}
