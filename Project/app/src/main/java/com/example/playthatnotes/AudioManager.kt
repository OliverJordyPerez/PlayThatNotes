package com.example.playthatnotes

import android.content.Context
import android.media.MediaPlayer

class AudioManager(private val context: Context) {
    fun reproduceSound(note: Note) {
        lateinit var mediaPlayer: MediaPlayer
        when (note) {
            Note.C5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.do5)
            }
            Note.D5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.re5)
            }
            Note.E5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.mi5)
            }
            Note.F5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.fa5)
            }
            Note.G5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.sol5)
            }
            Note.A5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.la5)
            }
            Note.B5 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.si5)
            }
            Note.C6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.do5)
            }
            Note.D6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.re5)
            }
            Note.E6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.mi5)
            }
            Note.F6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.fa5)
            }
            Note.G6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.sol5)
            }
            Note.A6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.la5)
            }
            Note.B6 -> {
                mediaPlayer = MediaPlayer.create(context, R.raw.si5)
            }
        }
        mediaPlayer.start()
    }
}