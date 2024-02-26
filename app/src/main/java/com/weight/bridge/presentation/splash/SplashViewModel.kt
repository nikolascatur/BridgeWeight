package com.weight.bridge.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weight.bridge.domain.manager.RepositoryManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val manager: RepositoryManager) : ViewModel() {

    private val _isFinish = mutableStateOf(false)
    val isFinish: State<Boolean> = _isFinish
    fun syncFromServer() {
        viewModelScope.launch {
            manager.syncFromServer()
            _isFinish.value = true
        }
    }
}