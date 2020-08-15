package com.example.playthatnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var currentNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNote(generateRandomNote())
        setButtonListeners()
    }

    private fun setButtonListeners() {
        cButton.setOnClickListener { handleAnswer(note = Note.C5) }
        dButton.setOnClickListener { handleAnswer(note = Note.D5) }
        eButton.setOnClickListener { handleAnswer(note = Note.E5) }
        fButton.setOnClickListener { handleAnswer(note = Note.F5) }
        gButton.setOnClickListener { handleAnswer(note = Note.G5) }
        aButton.setOnClickListener { handleAnswer(note = Note.A5) }
        bButton.setOnClickListener { handleAnswer(note = Note.B5) }
    }

    private fun generateRandomNote(): Note {
        return Note.values().toList().shuffled().first()
    }

    private fun handleAnswer(note: Note) {
        if (evaluateAnswer(note)) {
            setNote(generateRandomNote())
        } else {
            // TODO: Handle error
        }
    }

    private fun evaluateAnswer(note: Note): Boolean {
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

    private fun setNote(note: Note) {
        currentNote = note
        when (note) {
            Note.C5 -> positionNoteTo(firstLowerExtraLine)
            Note.D5 -> positionNoteTo(firstLowerSpace)
            Note.E5 -> positionNoteTo(firstLine)
            Note.F5 -> positionNoteTo(firstSpace)
            Note.G5 -> positionNoteTo(secondLine)
            Note.A5 -> positionNoteTo(secondSpace)
            Note.B5 -> positionNoteTo(thirdLine)
            Note.C6 -> positionNoteTo(thirdSpace)
            Note.D6 -> positionNoteTo(forthLine)
            Note.E6 -> positionNoteTo(forthSpace)
            Note.F6 -> positionNoteTo(fifthLine)
            Note.G6 -> positionNoteTo(firstUpperSpace)
            Note.A6 -> positionNoteTo(firstUpperExtraLine)
            Note.B6 -> positionNoteTo(secondUpperSpace)
        }
    }

    private fun positionNoteTo(line: View) {
        val params = noteImg.layoutParams as ConstraintLayout.LayoutParams
        params.leftToRight = line.id
        params.topToTop = line.id
        params.bottomMargin = 160
        params.bottomToBottom = line.id
        noteImg.requestLayout()
    }
}

enum class Note {
    C5, D5, E5, F5, G5, A5, B5, C6, D6, E6, F6, G6, A6, B6
}