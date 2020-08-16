package com.example.playthatnotes.main

import com.example.playthatnotes.helpers.Clef
import com.example.playthatnotes.helpers.Note
import com.example.playthatnotes.model.MapPrefs
import kotlin.math.round

class MainActivityPresenter {

    fun getFinalMessage(correctNotes: Int, wrongNotes: Int): String {
        return if (correctNotes >= 60 && wrongNotes == 0) {
            "You are a musical genius! But you can do it better. Keep practicing!"
        } else if(correctNotes < 60 && wrongNotes == 0) {
            "Not fast enough. Keep practicing to become a musical genius!"
        } else {
            "You made mistakes. Keep practicing to become a musical genius!"
        }
    }

    fun getFinalTempo(correctNotes: Int, wrongNotes: Int): String =
        if (correctNotes + wrongNotes >= 80) {
            "Your tempo: Prestissimo"
        } else if(correctNotes + wrongNotes >= 60) {
            "Your tempo: Presto"
        } else if(correctNotes + wrongNotes >= 40) {
            "Your tempo: Allegro"
        } else if(correctNotes + wrongNotes >= 20) {
            "Your tempo: Lento"
        } else {
            "Your tempo: Larghissimo"
        }

    fun generateRandomNote(currentNote: Note? = null): Note {
        val randomNote = Note.values().toList().shuffled().first()
        return if (randomNote == currentNote) {
             generateRandomNote(currentNote)
        } else {
            randomNote
        }
    }

    private fun getAbsoluteNote(note: Note?): Note? {
        when(note) {
            Note.C5, Note.C6 -> return  Note.C5
            Note.D5, Note.D6 -> return  Note.D5
            Note.E5, Note.E6 -> return  Note.E5
            Note.F5, Note.F6 -> return  Note.F5
            Note.G5, Note.G6 -> return  Note.G5
            Note.A5, Note.A6 -> return  Note.A5
            Note.B5, Note.B6 -> return  Note.B5
        }
        return null
    }

    fun getMusicalGeniusScore(correctNotes: Int, wrongNotes: Int): String {
        var score = ((correctNotes - wrongNotes).toDouble() / 60) * 100.0
        if (score < 0) {
            score = 0.0
        }
        return "Genius score: ${score.round(2) }%"
    }

    fun getFinalClef(): String {
        val clef = MapPrefs.getGameClefMode()
        return when(clef) {
            Clef.TREBLE -> "Treble clef"
            Clef.BASS -> "Bass clef"
        }
    }

    fun evaluateAnswer(note: Note, currentNote: Note?): Boolean {
        if (currentNote == note) {
            return true
        } else {
            when (note) {
                Note.C5 -> return currentNote == Note.C6
                Note.D5 -> return currentNote == Note.D6
                Note.E5 -> return currentNote == Note.E6
                Note.F5 -> return currentNote == Note.F6
                Note.G5 -> return currentNote == Note.G6
                Note.A5 -> return currentNote == Note.A6
                Note.B5 -> return currentNote == Note.B6
                Note.C6 -> return currentNote == Note.C5
                Note.D6 -> return currentNote == Note.D5
                Note.E6 -> return currentNote == Note.E5
                Note.F6 -> return currentNote == Note.F5
                Note.G6 -> return currentNote == Note.G5
                Note.A6 -> return currentNote == Note.A5
                Note.B6 -> return currentNote == Note.B5
            }
            return false
        }
    }

    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
}