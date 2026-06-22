package com.glasswidget.music.presentation.main

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glasswidget.music.domain.usecase.ObserveNowPlayingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    observeNowPlayingUseCase: ObserveNowPlayingUseCase
) : ViewModel() {

    private val notificationAccessGranted = MutableStateFlow(isListenerEnabled())

    val uiState = combine(notificationAccessGranted, observeNowPlayingUseCase()) { granted, track ->
        MainUiState(isNotificationAccessGranted = granted, nowPlaying = track)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MainUiState(isListenerEnabled(), null))

    fun refreshPermissionState() {
        notificationAccessGranted.value = isListenerEnabled()
    }

    private fun isListenerEnabled(): Boolean =
        NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)
}
