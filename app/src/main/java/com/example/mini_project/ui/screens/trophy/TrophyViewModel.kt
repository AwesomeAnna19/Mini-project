package com.example.mini_project.ui.screens.trophy

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.badge.Badge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrophyViewModel(private val container: AppContainer): ViewModel() {
    private val _uiState = MutableStateFlow(TrophyUiState())


    val trophyUiState: StateFlow<TrophyUiState> =
        _uiState.asStateFlow()

}

data class TrophyUiState(
    val badgeList: List<Badge> = listOf(),
)
