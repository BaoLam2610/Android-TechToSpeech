package com.lambao.ttsdemo

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(
    context: Context
) : TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val isVietnameseSupported = textToSpeech?.isLanguageAvailable(Locale("vi"))
            if (isVietnameseSupported == TextToSpeech.LANG_AVAILABLE || isVietnameseSupported == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                Log.d("lamnb", "onInit:  Vietnamese language is supported")
            } else {
                // Handle the case where Vietnamese is not available
                Log.d("lamnb", "onInit:  Vietnamese language not available")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }

        /*// For 21+
        val voices = textToSpeech?.voices
        val vietnameseVoice = voices?.find { it.locale == Locale("vi") }
        if (vietnameseVoice != null) {
            textToSpeech?.voice = vietnameseVoice
        }*/
    }

    fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stop() {
        textToSpeech?.stop()
    }

    fun shutdown() {
        textToSpeech?.shutdown()
    }
}