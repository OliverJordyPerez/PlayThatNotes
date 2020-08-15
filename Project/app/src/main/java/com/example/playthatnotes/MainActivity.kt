package com.example.playthatnotes

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentNote: Note? = null
    private var correctCount = 0
    private var wrongCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNote(generateRandomNote())
        setButtonListeners()
        updateScore()
    }

    private fun setButtonListeners() {
        cButton.setOnClickListener {
            reproduceSound(Note.C5)
            handleAnswer(note = Note.C5)
        }
        dButton.setOnClickListener {
            reproduceSound(Note.D5)
            handleAnswer(note = Note.D5)
        }
        eButton.setOnClickListener {
            reproduceSound(Note.E5)
            handleAnswer(note = Note.E5)
        }
        fButton.setOnClickListener {
            reproduceSound(Note.F5)
            handleAnswer(note = Note.F5)
        }
        gButton.setOnClickListener {
            reproduceSound(Note.G5)
            handleAnswer(note = Note.G5)
        }
        aButton.setOnClickListener {
            reproduceSound(Note.A5)
            handleAnswer(note = Note.A5)
        }
        bButton.setOnClickListener {
            reproduceSound(Note.B5)
            handleAnswer(note = Note.B5)
        }
    }

    private fun generateRandomNote(): Note {
        val randomNote = Note.values().toList().shuffled().first()
        return if (randomNote == this.currentNote) {
            generateRandomNote()
        } else {
            randomNote
        }
    }

    private fun handleAnswer(note: Note) {
        if (evaluateAnswer(note)) {
            correctCount ++
            setNote(generateRandomNote())
            showFeedbackColor(note, color = Color.GREEN)
        } else {
            wrongCount ++
            showFeedbackColor(note, color = Color.RED)
        }
        updateScore()
    }

    private fun showFeedbackColor(note: Note, color: Int) {
        when (note) {
            Note.C5, Note.C6 -> cButton.setBackgroundColor(color)
            Note.D5, Note.D6 -> dButton.setBackgroundColor(color)
            Note.E5, Note.E6 -> eButton.setBackgroundColor(color)
            Note.F5, Note.F6 -> fButton.setBackgroundColor(color)
            Note.G5, Note.G6 -> gButton.setBackgroundColor(color)
            Note.A5, Note.A6 -> aButton.setBackgroundColor(color)
            Note.B5, Note.B6 -> bButton.setBackgroundColor(color)
        }
    }

    private fun updateScore() {
        correctScore.text = "Correct: $correctCount"
        wrongScore.text = "Wrong: $wrongCount"
    }

    private fun reproduceSound(note: Note) {
        lateinit var mediaPlayer: MediaPlayer
        when (note) {
            Note.C5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.do5)
            }
            Note.D5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.re5)
            }
            Note.E5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.mi5)
            }
            Note.F5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.fa5)
            }
            Note.G5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.sol5)
            }
            Note.A5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.la5)
            }
            Note.B5 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.si5)
            }
            Note.C6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.do5)
            }
            Note.D6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.re5)
            }
            Note.E6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.mi5)
            }
            Note.F6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.fa5)
            }
            Note.G6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.sol5)
            }
            Note.A6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.la5)
            }
            Note.B6 -> {
                mediaPlayer = MediaPlayer.create(this, R.raw.si5)
            }
        }
        mediaPlayer.start()
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
        this.currentNote = note
        when (note) {
            Note.C5 -> {
                toggleExtraLinesVisibility(bottom = true)
                positionNoteTo(firstLowerExtraLine)
            }
            Note.D5 -> {
                toggleExtraLinesVisibility(bottom = true)
                positionNoteTo(firstLowerSpace)
            }
            Note.E5 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(firstLine)
            }
            Note.F5 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(firstSpace)
            }
            Note.G5 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(secondLine)
            }
            Note.A5 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(secondSpace)
            }
            Note.B5 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(thirdLine)
            }
            Note.C6 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(thirdSpace)
            }
            Note.D6 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(forthLine)
            }
            Note.E6 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(forthSpace)
            }
            Note.F6 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(fifthLine)
            }
            Note.G6 -> {
                toggleExtraLinesVisibility()
                positionNoteTo(firstUpperSpace)
            }
            Note.A6 -> {
                toggleExtraLinesVisibility(top = true)
                positionNoteTo(firstUpperExtraLine)
            }
            Note.B6 -> {
                toggleExtraLinesVisibility(top = true)
                positionNoteTo(secondUpperSpace)
            }
        }
    }

    private fun toggleExtraLinesVisibility(bottom: Boolean = false, top: Boolean = false) {
        when {
            bottom -> {
                firstLowerExtraLine.visibility = View.VISIBLE
                firstUpperExtraLine.visibility = View.INVISIBLE
            }
            top -> {
                firstUpperExtraLine.visibility = View.VISIBLE
                firstLowerExtraLine.visibility = View.INVISIBLE
            }
            else -> {
                firstLowerExtraLine.visibility = View.INVISIBLE
                firstUpperExtraLine.visibility = View.INVISIBLE
            }
        }
    }

    private fun positionNoteTo(line: View) {
        val params = noteImg.layoutParams as ConstraintLayout.LayoutParams
        params.leftToRight = line.id
        params.topToTop = line.id
        params.bottomMargin = 0
        params.bottomToBottom = line.id
        noteImg.requestLayout()
    }
}

enum class Note {
    C5, D5, E5, F5, G5, A5, B5, C6, D6, E6, F6, G6, A6, B6
}