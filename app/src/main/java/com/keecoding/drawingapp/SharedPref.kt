package com.keecoding.drawingapp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPref {
    private var sharedPreferences: SharedPreferences? = null
    private var activity = 1

    fun init(context: Context) {
        MusicPlayer.init(context)
        sharedPreferences = context.getSharedPreferences("DRAWING PREF", Context.MODE_PRIVATE)

        if (isMusicOn) {
            MusicPlayer.reset()
            MusicPlayer.play()
        }
    }

    fun setActivity(i: Int) {
        activity = i
    }

    fun pause(i: Int) {
        if (i == activity) {
            MusicPlayer.pause()
            Log.d("aaa", "pause: ")
        }
    }

    fun resume(i: Int) {
        if (i == activity && isMusicOn) {
            MusicPlayer.play()
        }
    }

    var isMusicOn: Boolean
        get() = sharedPreferences?.getBoolean("MUSIC", true)!!
        set(value) {
            sharedPreferences?.edit()?.putBoolean("MUSIC", value)?.apply()
            if (isMusicOn) {
                Log.d("aaa", "$isMusicOn")
                MusicPlayer.play()
                MusicPlayer.reset()
                MusicPlayer.play()
            } else {
                Log.d("aaa", "$isMusicOn")
                MusicPlayer.stop()
            }
        }
}