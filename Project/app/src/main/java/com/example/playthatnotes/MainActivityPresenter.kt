package com.example.playthatnotes

class MainActivityPresenter {
    fun generateRandomNote(currentNote: Note?): Note {
        val randomNote = Note.values().toList().shuffled().first()
        return if (randomNote == currentNote) {
            generateRandomNote(currentNote)
        } else {
            randomNote
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
}