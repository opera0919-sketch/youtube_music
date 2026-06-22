package com.glasswidget.music.data.util

import android.graphics.Bitmap

/**
 * Software box blur. Runs on a quarter-scale copy of the source so the
 * repeated passes stay cheap enough to run on every album art change
 * without a GPU-backed RenderEffect (which requires a live View hierarchy).
 */
object BitmapBlurUtil {

    private const val DOWNSCALE = 0.25f
    private const val PASSES = 3

    fun blur(source: Bitmap, radius: Int): Bitmap {
        val scaledWidth = (source.width * DOWNSCALE).toInt().coerceAtLeast(1)
        val scaledHeight = (source.height * DOWNSCALE).toInt().coerceAtLeast(1)
        val downscaled = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, true)

        val blurRadius = (radius * DOWNSCALE).toInt().coerceAtLeast(1)
        val blurred = boxBlur(downscaled, blurRadius)

        return Bitmap.createScaledBitmap(blurred, source.width, source.height, true)
    }

    private fun boxBlur(bitmap: Bitmap, radius: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        repeat(PASSES) {
            horizontalPass(pixels, width, height, radius)
            verticalPass(pixels, width, height, radius)
        }

        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            setPixels(pixels, 0, width, 0, 0, width, height)
        }
    }

    private fun horizontalPass(pixels: IntArray, width: Int, height: Int, radius: Int) {
        for (y in 0 until height) {
            val rowStart = y * width
            for (x in 0 until width) {
                var a = 0
                var r = 0
                var g = 0
                var b = 0
                var count = 0
                for (dx in -radius..radius) {
                    val xx = x + dx
                    if (xx in 0 until width) {
                        val pixel = pixels[rowStart + xx]
                        a += (pixel ushr 24) and 0xFF
                        r += (pixel ushr 16) and 0xFF
                        g += (pixel ushr 8) and 0xFF
                        b += pixel and 0xFF
                        count++
                    }
                }
                pixels[rowStart + x] = packArgb(a / count, r / count, g / count, b / count)
            }
        }
    }

    private fun verticalPass(pixels: IntArray, width: Int, height: Int, radius: Int) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                var a = 0
                var r = 0
                var g = 0
                var b = 0
                var count = 0
                for (dy in -radius..radius) {
                    val yy = y + dy
                    if (yy in 0 until height) {
                        val pixel = pixels[yy * width + x]
                        a += (pixel ushr 24) and 0xFF
                        r += (pixel ushr 16) and 0xFF
                        g += (pixel ushr 8) and 0xFF
                        b += pixel and 0xFF
                        count++
                    }
                }
                pixels[y * width + x] = packArgb(a / count, r / count, g / count, b / count)
            }
        }
    }

    private fun packArgb(a: Int, r: Int, g: Int, b: Int): Int =
        (a shl 24) or (r shl 16) or (g shl 8) or b
}
