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

private const val SCRIM_ALPHA_DARK_ART = 0.15f
private const val SCRIM_ALPHA_LIGHT_ART = 0.45f
private const val SCRIM_ALPHA_NO_ART = 0.35f

/**
 * Glassmorphism background per the UI spec: a blurred album art backdrop
 * (Blur 16dp) under a translucent rounded scrim (24dp radius). Foreground
 * content is always rendered in white, so the scrim's opacity is the
 * adaptive-contrast lever: light artwork gets a darker scrim to keep white
 * text/icons readable, dark artwork keeps the lighter 15% scrim from the
 * UI spec.
 */
@Composable
fun GlassPanel(
    backgroundArt: Bitmap?,
    isOnLightArtwork: Boolean = false,
    modifier: GlanceModifier = GlanceModifier,
    content: @Composable () -> Unit
) {
    val scrimAlpha = when {
        backgroundArt == null -> SCRIM_ALPHA_NO_ART
        isOnLightArtwork -> SCRIM_ALPHA_LIGHT_ART
        else -> SCRIM_ALPHA_DARK_ART
    }
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
                .background(Color.Black.copy(alpha = scrimAlpha))
        ) {
            content()
        }
    }
}
