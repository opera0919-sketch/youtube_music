package com.glasswidget.music.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.glasswidget.music.R

@Composable
fun EmptyStateLayout() {
    val context = LocalContext.current
    GlassPanel(backgroundArt = null) {
        Box(
            modifier = GlanceModifier.fillMaxSize().padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = context.getString(R.string.widget_empty_state),
                style = TextStyle(fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
            )
        }
    }
}
