package com.glasswidget.music.widget.ui

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.fillMaxSize

/**
 * Glassmorphism background per the UI spec: a blurred album art backdrop
 * (Blur 16dp) under a translucent rounded scrim (15% opacity, 24dp radius).
 */
@Composable
fun GlassPanel(
    backgroundArt: Bitmap?,
    modifier: GlanceModifier = GlanceModifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize().cornerRadius(24.dp)) {
        if (backgroundArt != null) {
            Image(
                provider = ImageProvider(backgroundArt),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = GlanceModifier.fillMaxSize()
            )
        }
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.15f))
        ) {
            content()
        }
    }
}
