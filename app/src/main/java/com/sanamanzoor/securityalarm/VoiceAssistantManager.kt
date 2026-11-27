package com.sanamanzoor.securityalarm

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class VoiceAssistantManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
            isReady = true
        }
    }

    fun speak(message: String) {
        if (isReady) {
            tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "ALARM_TTS_ID")
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
