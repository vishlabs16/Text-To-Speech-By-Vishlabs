package com.rach.texttospeechbyvishlabs.data.tts


import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale


/*
class AdvancedTTSManagers(context: Context) {

    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
    }

    fun speak(text: String) {

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_id")
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }

    fun getInstalledVoices(): List<Voice> {
        return tts?.voices?.toList() ?: emptyList()
    }

    fun setVoice(voice: Voice) {
        tts?.voice = voice
    }
} */

class AdvancedTTSManagers(context: Context) {

    private var tts: TextToSpeech? = null

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
    }

    fun speak(text: String) {

        val utteranceId = "tts_id"

        tts?.setOnUtteranceProgressListener(object :
            android.speech.tts.UtteranceProgressListener() {

            override fun onStart(id: String?) {
                if (id == utteranceId) {
                    _isSpeaking.value = true
                }
            }

            override fun onDone(id: String?) {
                if (id == utteranceId) {
                    _isSpeaking.value = false
                }
            }

            override fun onError(id: String?) {
                _isSpeaking.value = false
            }
        })

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        _isSpeaking.value = false
    }

    fun getInstalledVoices(): List<Voice> {
        return tts?.voices?.toList() ?: emptyList()
    }

    fun setVoice(voice: Voice) {
        tts?.voice = voice
    }
}
