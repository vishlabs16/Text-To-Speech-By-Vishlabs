package com.rach.texttospeechbyvishlabs.presentation.viewmodel

import android.speech.tts.Voice
import androidx.lifecycle.ViewModel
import com.rach.texttospeechbyvishlabs.data.tts.AdvancedTTSManagers
import com.rach.texttospeechbyvishlabs.domain.model.TtsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TtsViewModels(
    private val ttsManager: AdvancedTTSManagers
) : ViewModel() {

    private val _state = MutableStateFlow(TtsState())
    val state: StateFlow<TtsState> = _state



    val isplay: StateFlow<Boolean> = ttsManager.isSpeaking

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun stop() {
        ttsManager.stop()
    }

    override fun onCleared() {
        ttsManager.shutdown()
        super.onCleared()
    }


    fun loadVoices() {
        _state.value = _state.value.copy(
            voices = ttsManager.getInstalledVoices()
        )
    }

    fun selectVoice(voice: Voice) {
        ttsManager.setVoice(voice)
        _state.value = _state.value.copy(selectedVoice = voice)
    }




}
