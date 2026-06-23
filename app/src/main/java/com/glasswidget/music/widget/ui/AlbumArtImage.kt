package com.glasswidget.music.widget.ui

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.cornerRadius
import androidx.glance.layout.ContentScale
import androidx.glance.layout.size
import com.glasswidget.music.R

@Composable
fun AlbumArtImage(bitmap: Bitmap?, size: Dp) {
    Image(
        provider = bitmap?.let { ImageProvider(it) } ?: ImageProvider(R.drawable.ic_music_note),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = GlanceModifier.size(size).cornerRadius(12.dp)
    )
}
