package com.glasswidget.music.data.album

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import com.glasswidget.music.core.Constants
import com.glasswidget.music.data.util.BitmapBlurUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

data class ResolvedAlbumArt(
    val foreground: Bitmap?,
    val blurredBackground: Bitmap?,
    val isLightArtwork: Boolean
)

private const val MAX_FOREGROUND_DIMENSION = 256

/**
 * Resolves the sharp foreground artwork, a blurred background copy (for the
 * glassmorphism backdrop) and a light/dark hint used to pick a readable text
 * color (FR-002, Adaptive Contrast).
 */
@Singleton
class AlbumArtResolver @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) {

    suspend fun resolve(embeddedBitmap: Bitmap?, artUri: String?): ResolvedAlbumArt {
        val rawArt = embeddedBitmap ?: artUri?.let { loadFromUri(it) }
            ?: return ResolvedAlbumArt(foreground = null, blurredBackground = null, isLightArtwork = false)

        // Source art (e.g. a 1024x1024 MediaMetadata bitmap) is far larger
        // than anything the widget displays. Downscaling before it enters a
        // RemoteViews tree keeps the payload well under the Binder
        // transaction / widget bitmap-memory limits.
        val art = downscale(rawArt, MAX_FOREGROUND_DIMENSION)
        val blurred = BitmapBlurUtil.blur(art, Constants.ALBUM_ART_BLUR_RADIUS)
        return ResolvedAlbumArt(
            foreground = art,
            blurredBackground = blurred,
            isLightArtwork = isLightArtwork(art)
        )
    }

    private fun downscale(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val largestSide = maxOf(bitmap.width, bitmap.height)
        if (largestSide <= maxDimension) return bitmap
        val scale = maxDimension.toFloat() / largestSide
        val width = (bitmap.width * scale).toInt().coerceAtLeast(1)
        val height = (bitmap.height * scale).toInt().coerceAtLeast(1)
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private suspend fun loadFromUri(uri: String): Bitmap? = runCatching {
        val request = ImageRequest.Builder(context)
            .data(uri)
            .allowHardware(false)
            .build()
        imageLoader.execute(request).drawable?.toBitmap()
    }.getOrNull()

    private fun isLightArtwork(bitmap: Bitmap): Boolean = runCatching {
        val swatch = Palette.from(bitmap).generate().dominantSwatch ?: return@runCatching false
        val color = swatch.rgb
        val r = (color shr 16 and 0xFF) / 255.0
        val g = (color shr 8 and 0xFF) / 255.0
        val b = (color and 0xFF) / 255.0
        val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b
        luminance > 0.6
    }.getOrDefault(false)
}
