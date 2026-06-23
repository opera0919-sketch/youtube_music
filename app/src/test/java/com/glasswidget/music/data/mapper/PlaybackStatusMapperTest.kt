package com.glasswidget.music.data.mapper

import android.media.session.PlaybackState
import com.glasswidget.music.domain.model.PlaybackStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaybackStatusMapperTest {

    @Test
    fun `playing state maps to PLAYING`() {
        assertEquals(PlaybackStatus.PLAYING, mapPlaybackStateToStatus(PlaybackState.STATE_PLAYING))
    }

    @Test
    fun `buffering state maps to PLAYING`() {
        assertEquals(PlaybackStatus.PLAYING, mapPlaybackStateToStatus(PlaybackState.STATE_BUFFERING))
    }

    @Test
    fun `paused state maps to PAUSED`() {
        assertEquals(PlaybackStatus.PAUSED, mapPlaybackStateToStatus(PlaybackState.STATE_PAUSED))
    }

    @Test
    fun `stopped state maps to STOPPED`() {
        assertEquals(PlaybackStatus.STOPPED, mapPlaybackStateToStatus(PlaybackState.STATE_STOPPED))
    }

    @Test
    fun `null state maps to NONE`() {
        assertEquals(PlaybackStatus.NONE, mapPlaybackStateToStatus(null))
    }
}
