package com.glasswidget.music.widget

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * Approximate dp footprint of each supported launcher grid span (FR-005).
 * Exact cell size varies per launcher, so these are conservative midpoints
 * used to pick a layout tier via [androidx.glance.appwidget.SizeMode.Responsive].
 */
object GlassWidgetSizes {
    val SMALL_2X2 = DpSize(110.dp, 110.dp)
    val MEDIUM_4X2 = DpSize(250.dp, 110.dp)
    val MEDIUM_4X3 = DpSize(250.dp, 180.dp)
    val LARGE_5X3 = DpSize(320.dp, 180.dp)
    val LARGE_5X4 = DpSize(320.dp, 250.dp)

    val ALL = setOf(SMALL_2X2, MEDIUM_4X2, MEDIUM_4X3, LARGE_5X3, LARGE_5X4)
}
