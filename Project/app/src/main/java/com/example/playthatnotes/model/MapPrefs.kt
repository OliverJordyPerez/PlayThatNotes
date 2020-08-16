package com.example.playthatnotes.model

import androidx.preference.PreferenceManager
import com.example.playthatnotes.app.App
import com.example.playthatnotes.helpers.Clef


object MapPrefs {

    private val KEY_CLEF_MODE = "KEY_CLEF_MODE"
    private val KEY_SOUND_SWITCH = "KEY_SOUND_SWITCH"

    fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(App.instance.getContext())

    fun saveGameClefMode(clef: Clef) {
        val editor = sharedPrefs().edit()
        editor.putString(KEY_CLEF_MODE, clef.toString()).apply()
    }

    fun saveSoundToggle(state: Boolean) {
        val editor = sharedPrefs().edit()
        editor.putBoolean(KEY_SOUND_SWITCH, state).apply()
    }

    fun getGameClefMode(): Clef = Clef.TREBLE.toMyEnum(sharedPrefs().getString(KEY_CLEF_MODE, ""))

    fun getSoundToggle(): Boolean = sharedPrefs().getBoolean(KEY_SOUND_SWITCH, true)
}