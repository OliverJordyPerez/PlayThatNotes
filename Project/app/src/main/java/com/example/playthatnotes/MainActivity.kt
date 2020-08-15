package com.example.playthatnotes

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var presenter = MainActivityPresenter()
    val audioManager = AudioManager(this)

    private var currentNote: Note? = null
    private var correctCount = 0
    private var wrongCount = 0

    private var gameStarted = false

    lateinit var countDownTimer: CountDownTimer
    var initialCountDown: Long = 60000
    var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNote(presenter.generateRandomNote(currentNote))
        setButtonListeners()
        resetGame()
        startGame()
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, "Your had ${correctCount} correct and ${wrongCount} failed. Keep practicing if you want to be a musical genius", Toast.LENGTH_LONG).show()
        resetGame()
    }

    private fun resetGame() {
        correctCount = 0
        wrongCount = 0

        updateScore()

        val initialTimeLeft = initialCountDown / 1000
        timeLeftLabel.text = "Time left: ${initialTimeLeft}s"

        countDownTimer = object  : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
               val timeLeft = millisUntilFinished / 1000
                timeLeftLabel.text = "Time left: ${timeLeft}s"
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setButtonListeners() {
        cButton.setOnTouchListener { view, event ->
            handleAnswer(Note.C5, view, event)
        }

        dButton.setOnTouchListener { view, event ->
            handleAnswer(Note.D5, view, event)
        }
        eButton.setOnTouchListener { view, event ->
            handleAnswer(Note.E5, view, event)
        }
        fButton.setOnTouchListener { view, event ->
            handleAnswer(Note.F5, view, event)
        }
        gButton.setOnTouchListener { view, event ->
            handleAnswer(Note.G5, view, event)
        }
        aButton.setOnTouchListener { view, event ->
            handleAnswer(Note.A5, view, event)
        }
        bButton.setOnTouchListener { view, event ->
            handleAnswer(Note.B5, view, event)
        }
    }

    private fun handleAnswer(note: Note, button: View, event: MotionEvent): Boolean {
        var color = Color.GREEN
        if (presenter.evaluateAnswer(note, currentNote)) {
            correctCount ++
            setNote(presenter.generateRandomNote(currentNote))
        } else {
            wrongCount ++
            color = Color.RED
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                audioManager.reproduceSound(note)
                button.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                button.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                audioManager.stopSound()
                button.background.clearColorFilter()
                button.invalidate()
            }
        }
        updateScore()
        return false
    }

    private fun updateScore() {
        correctScore.text = "Correct: $correctCount"
        wrongScore.text = "Wrong: $wrongCount"
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
