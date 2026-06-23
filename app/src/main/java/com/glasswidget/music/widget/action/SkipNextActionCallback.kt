package com.glasswidget.music.widget.action

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.glasswidget.music.di.NowPlayingEntryPoint
import dagger.hilt.android.EntryPointAccessors

class SkipNextActionCallback : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        EntryPointAccessors.fromApplication(context, NowPlayingEntryPoint::class.java)
            .nowPlayingRepository()
            .skipToNext()
    }
}
