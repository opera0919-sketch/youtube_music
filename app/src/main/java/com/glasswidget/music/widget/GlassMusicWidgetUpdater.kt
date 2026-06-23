package com.glasswidget.music.widget

import android.content.Context
import com.glasswidget.music.core.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Wakes up (or refreshes) the Glance composition session for the widget.
 * Once a session is running it keeps itself live by collecting the
 * repository's Flow directly inside [GlassMusicWidget.provideGlance], so this
 * only needs to be called on session start/stop transitions.
 */
@Singleton
class GlassMusicWidgetUpdater @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope private val scope: CoroutineScope
) {
    fun requestUpdate() {
        scope.launch {
            GlassMusicWidget().updateAll(context)
        }
    }
}
