package com.rach.texttospeechbyvishlabs.data.tts


import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.Locale

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
}
