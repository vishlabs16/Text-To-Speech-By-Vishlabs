package com.rach.texttospeechbyvishlabs.domain.model



import android.speech.tts.Voice

data class TtsState(
    val voices: List<Voice> = emptyList(),
    val selectedVoice: Voice? = null
)

