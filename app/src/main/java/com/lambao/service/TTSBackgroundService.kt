package com.lambao.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.lambao.ttsdemo.TextToSpeechHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TTSBackgroundService : Service() {

    private lateinit var ttsHelper: TextToSpeechHelper

    override fun onCreate() {
        super.onCreate()
        ttsHelper = TextToSpeechHelper.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val textToSpeak = intent?.getStringExtra("textToSpeak")
        if (!textToSpeak.isNullOrEmpty()) {
            Log.d("lamnb", "onStartCommand ${textToSpeak}")
            CoroutineScope(Dispatchers.Main).launch {
                delay(300)
                ttsHelper.speakWithCallback(textToSpeak) {
                    this.cancel()
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsHelper.shutdown() // Clean up resources
    }

    override fun onBind(intent: Intent?): IBinder? = null
}