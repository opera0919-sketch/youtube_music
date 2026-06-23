package com.glasswidget.music.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.glance.GlanceId
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.material3.GlanceTheme
import com.glasswidget.music.di.NowPlayingEntryPoint
import com.glasswidget.music.domain.model.NowPlayingTrack
import com.glasswidget.music.widget.ui.EmptyStateLayout
import com.glasswidget.music.widget.ui.LargeLayout
import com.glasswidget.music.widget.ui.MediumLayout
import com.glasswidget.music.widget.ui.SmallLayout
import dagger.hilt.android.EntryPointAccessors

class GlassMusicWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Responsive(GlassWidgetSizes.ALL)

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = EntryPointAccessors
            .fromApplication(context, NowPlayingEntryPoint::class.java)
            .nowPlayingRepository()

        provideContent {
            // minSdk 31 guarantees Android 12+, so GlanceTheme always resolves
            // the system dynamic (Material You) color scheme here.
            GlanceTheme {
                val track by repository.observeNowPlaying().collectAsState(initial = null)
                GlassMusicContent(track)
            }
        }
    }
}

@Composable
private fun GlassMusicContent(track: NowPlayingTrack?) {
    val size = LocalSize.current
    when {
        track == null || track.title.isBlank() -> EmptyStateLayout()
        size.height <= GlassWidgetSizes.SMALL_2X2.height -> SmallLayout(track)
        size.height <= GlassWidgetSizes.MEDIUM_4X3.height -> MediumLayout(track)
        else -> LargeLayout(track)
    }
}
