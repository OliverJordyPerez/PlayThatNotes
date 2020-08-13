package com.example.playthatnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNote(Note.E6)
    }

    private fun setNote(note: Note) {
        when (note) {
            Note.C5 -> positionNoteTo(firstLowerExtraLine)
            Note.D5 -> print("Add first lower extra space")
            Note.E5 -> positionNoteTo(firstLine)
            Note.F5 -> positionNoteTo(firstSpace)
            Note.G5 -> positionNoteTo(secondLine)
            Note.A5 -> positionNoteTo(secondSpace)
            Note.B5 -> positionNoteTo(thirdLine)
            Note.C6 -> positionNoteTo(thirdSpace)
            Note.D6 -> positionNoteTo(forthLine)
            Note.E6 -> positionNoteTo(forthSpace)
            Note.F6 -> positionNoteTo(fifthLine)
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
    C5, D5, E5, F5, G5, A5, B5, C6, D6, E6, F6
}