package com.example.playthatnotes.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.playthatnotes.helpers.AudioManager
import com.example.playthatnotes.helpers.Note
import com.example.playthatnotes.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_game_over.*
import kotlinx.android.synthetic.main.dialog_game_over.view.*

const val CORRECT_COUNT = "correctCount"
const val WRONG_COUNT = "wrongCount"

class MainActivity : AppCompatActivity() {

    var presenter = MainActivityPresenter()
    val audioManager = AudioManager(this)

    private var currentNote: Note? = null
    private var correctCount = 0
    private var wrongCount = 0

    private var gameStarted = false

    lateinit var countDownTimer: CountDownTimer
    var initialCountDown: Long = 2000
    var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNote(presenter.generateRandomNote(currentNote))
        setNoteButtonsListeners()
        resetGame()
        mainStartButton.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        mainStartButton.visibility = View.INVISIBLE
        noteImg.visibility = View.VISIBLE
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        mainStartButton.visibility = View.INVISIBLE
        showGameOverDialog()
    }

    private fun showGameOverDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_game_over, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setPositiveButton("Close") { _, _ ->
            resetGame()
        }
        dialogView.finalCorrectLabel.text = correctScore.text
        dialogView.finalWrong.text = wrongScore.text
        dialogView.quoteLabel.text = presenter.getFinalMessage(correctCount, wrongCount)
        dialogView.yourTempo.text = presenter.getFinalTempo(correctCount, wrongCount)
        dialog.show()
    }

    private fun resetGame() {
        toggleExtraLinesVisibility()
        mainStartButton.visibility = View.VISIBLE
        noteImg.visibility = View.INVISIBLE
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
    private fun setNoteButtonsListeners() {
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
