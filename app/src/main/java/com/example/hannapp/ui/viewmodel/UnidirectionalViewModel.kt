package com.example.hannapp.ui.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface UnidirectionalViewModel<STATE, EVENT> {
    val state: StateFlow<STATE>
    fun event(event: EVENT)
}
