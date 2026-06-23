package com.glasswidget.music.service

import android.content.ComponentName
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.glasswidget.music.core.Constants
import com.glasswidget.music.data.mediasession.NowPlayingDataSource
import com.glasswidget.music.widget.GlassMusicWidgetUpdater
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * YouTube Music exposes no public API, so the only reliable, permission-safe
 * way to read "now playing" state is via [MediaSessionManager], which
 * requires this process to be a bound notification listener. This service's
 * only job is bridging the framework MediaController callbacks into
 * [NowPlayingDataSource].
 */
@AndroidEntryPoint
class YouTubeMusicListenerService : NotificationListenerService() {

    @Inject
    lateinit var nowPlayingDataSource: NowPlayingDataSource

    @Inject
    lateinit var widgetUpdater: GlassMusicWidgetUpdater

    private var mediaSessionManager: MediaSessionManager? = null
    private var activeController: MediaController? = null

    private val controllerCallback = object : MediaController.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadata?) {
            nowPlayingDataSource.onMetadataChanged(metadata)
        }

        override fun onPlaybackStateChanged(state: PlaybackState?) {
            nowPlayingDataSource.onPlaybackStateChanged(state)
        }

        override fun onSessionDestroyed() {
            detachCurrentController()
            refreshActiveSession()
        }
    }

    private val sessionsChangedListener =
        MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
            handleActiveSessions(controllers.orEmpty())
        }

    override fun onListenerConnected() {
        super.onListenerConnected()
        val manager = getSystemService(MediaSessionManager::class.java)
        mediaSessionManager = manager
        manager.addOnActiveSessionsChangedListener(sessionsChangedListener, listenerComponent())
        refreshActiveSession()
    }

    override fun onListenerDisconnected() {
        mediaSessionManager?.removeOnActiveSessionsChangedListener(sessionsChangedListener)
        detachCurrentController()
        super.onListenerDisconnected()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn?.packageName == Constants.YOUTUBE_MUSIC_PACKAGE) {
            refreshActiveSession()
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        if (sbn?.packageName == Constants.YOUTUBE_MUSIC_PACKAGE) {
            refreshActiveSession()
        }
    }

    private fun refreshActiveSession() {
        val manager = mediaSessionManager ?: return
        val controllers = runCatching { manager.getActiveSessions(listenerComponent()) }
            .getOrDefault(emptyList())
        handleActiveSessions(controllers)
    }

    private fun handleActiveSessions(controllers: List<MediaController>) {
        val target = controllers.firstOrNull { it.packageName == Constants.YOUTUBE_MUSIC_PACKAGE }

        if (target == null) {
            if (activeController != null) {
                detachCurrentController()
                widgetUpdater.requestUpdate()
            }
            return
        }

        if (activeController?.sessionToken == target.sessionToken) return

        detachCurrentController()
        activeController = target
        target.registerCallback(controllerCallback)
        nowPlayingDataSource.attach(target)
        widgetUpdater.requestUpdate()
    }

    private fun detachCurrentController() {
        activeController?.unregisterCallback(controllerCallback)
        activeController = null
        nowPlayingDataSource.detach()
    }

    private fun listenerComponent() = ComponentName(this, YouTubeMusicListenerService::class.java)
}
