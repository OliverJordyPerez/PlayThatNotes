package com.example.playthatnotes.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.playthatnotes.helpers.AudioManager
import com.example.playthatnotes.helpers.Note
import com.example.playthatnotes.R
import com.example.playthatnotes.helpers.Clef
import com.example.playthatnotes.model.MapPrefs
import com.example.playthatnotes.settings.CLEF_CLANGED
import com.example.playthatnotes.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_game_over.view.*

const val CORRECT_COUNT = "correctCount"
const val WRONG_COUNT = "wrongCount"

class MainActivity : AppCompatActivity() {

    var presenter = MainActivityPresenter()
    val audioManager by lazy { AudioManager(this) }
    private var isBassClef = MapPrefs.getGameClefMode() == Clef.BASS

    private var currentNote: Note? = null
    private var correctCount = 0
    private var wrongCount = 0

    private var gameStarted = false

    var countDownTimer: CountDownTimer? = null
    var initialCountDown: Long = 60000
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

    private fun setClefOffset(clef: Clef) {
        when (clef) {
            Clef.BASS -> positionClef(110)
            Clef.TREBLE -> positionClef(160)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivityForResult(intent, 0)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val clefChanged = data?.getBooleanExtra(CLEF_CLANGED, false) ?: false
                if (clefChanged) {
                    resetGame()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startGame() {
        mainStartButton.visibility = View.INVISIBLE
        toggleButtonsVisibility(hide = false)
        setNote(presenter.generateRandomNote())
        countDownTimer?.start()
        gameStarted = true
    }

    private fun endGame() {
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
        dialogView.clefInfoLabel.text = presenter.getFinalClef()
        dialogView.musicalGeniusScore.text =
            presenter.getMusicalGeniusScore(correctCount, wrongCount)
        dialog.show()
    }

    private fun setClef() {
        isBassClef = MapPrefs.getGameClefMode() == Clef.BASS
        if (isBassClef) {
            clefImage.setImageResource(R.drawable.bass_clef)
            setClefOffset(Clef.BASS)
        } else {
            clefImage.setImageResource(R.drawable.g_cleff)
            setClefOffset(Clef.TREBLE)
        }
    }

    private fun resetGame() {
        setClef()
        toggleExtraLinesVisibility()
        mainStartButton.visibility = View.VISIBLE
        correctCount = 0
        wrongCount = 0
        countDownTimer?.cancel()

        updateScore()

        val initialTimeLeft = initialCountDown / 1000
        timeLeftLabel.text = "Time left: ${initialTimeLeft}s"

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftLabel.text = "Time left: ${timeLeft}s"
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
        toggleButtonsVisibility(true)
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

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (presenter.evaluateAnswer(note, currentNote)) {
                    correctCount++
                    setNote(presenter.generateRandomNote(currentNote))
                } else {
                    wrongCount++
                    color = Color.RED
                }
                audioManager.reproduceSound(note)
                button.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                button.invalidate()
            }
            MotionEvent.ACTION_UP -> {
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
        this.currentNote = if (isBassClef) translateToBassClef(note) else note
        when (note) {
            Note.C5 -> {
                toggleExtraLinesVisibility(bottom = true)
                positionNoteTo(firstLowerExtraLine)
            }
            Note.D5 -> {
                toggleExtraLinesVisibility()
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

    private fun positionClef(offset: Int) {
        val params = clefImage.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = offset
        clefImage.requestLayout()
    }

    private fun toggleButtonsVisibility(hide: Boolean) {
        val visibility = if (hide == true) View.INVISIBLE else View.VISIBLE
        noteImg.visibility = visibility
        cButton.visibility = visibility
        dButton.visibility = visibility
        eButton.visibility = visibility
        fButton.visibility = visibility
        gButton.visibility = visibility
        aButton.visibility = visibility
        bButton.visibility = visibility
    }

    private fun translateToBassClef(note: Note): Note {
        when (note) {
            Note.C5 -> return Note.E5
            Note.D5 -> return Note.F5
            Note.E5 -> return Note.G5
            Note.F5 -> return Note.A5
            Note.G5 -> return Note.B5
            Note.A5 -> return Note.C5
            Note.B5 -> return Note.D5
            Note.C6 -> return Note.E6
            Note.D6 -> return Note.F6
            Note.E6 -> return Note.G6
            Note.F6 -> return Note.A6
            Note.G6 -> return Note.B6
            Note.A6 -> return Note.C6
            Note.B6 -> return Note.D6
        }
        return Note.C5
    }
}
