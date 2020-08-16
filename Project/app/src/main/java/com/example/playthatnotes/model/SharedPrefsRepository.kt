package com.example.playthatnotes.model

import android.content.Context
import com.example.playthatnotes.app.App
import com.example.playthatnotes.helpers.Clef


interface GameRepository {
    fun getGameplayMode(): Clef
    fun getNoteSoundSwitchStatus(): Boolean
}

object SharedPrefsRepository: GameRepository {

    private const val SHARED_PREFS_REPO = "SHARED_PREFS_REPO"

    fun sharedPrefs() = App.instance.getContext().getSharedPreferences(SHARED_PREFS_REPO, Context.MODE_PRIVATE)

    override fun getGameplayMode(): Clef {
        TODO("Not yet implemented")
    }

    override fun getNoteSoundSwitchStatus(): Boolean {
        TODO("Not yet implemented")
    }

}