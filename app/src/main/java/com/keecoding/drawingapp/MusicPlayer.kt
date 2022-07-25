package com.keecoding.drawingapp

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun init(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.music)
    }

    fun reset() {
        mediaPlayer?.seekTo(0)
    }

    fun play() {
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true
    }

    fun stop() {
        mediaPlayer?.pause()
    }

    fun pause() {
        mediaPlayer?.pause()
    }
}