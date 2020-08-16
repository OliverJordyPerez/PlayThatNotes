package com.example.playthatnotes.settings

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.playthatnotes.R
import com.example.playthatnotes.model.MapPrefs
import kotlinx.android.synthetic.main.activity_settings.*
import com.example.playthatnotes.helpers.Clef.*


const val CLEF_CLANGED = "CLEF_CHANGED"
const val SOUND_TOGGLE = "SOUND_TOGGLE"

class SettingsActivity : AppCompatActivity() {

    private var items = arrayOf("Trebble", "Bass")
    private var currentClef = MapPrefs.getGameClefMode()
    private var clefChanged = false
    private var soundSwitchIsChecked = MapPrefs.getSoundToggle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupSpinner()
        swichSound.isChecked = soundSwitchIsChecked
        swichSound.setOnCheckedChangeListener { _, isChecked ->
            soundSwitchIsChecked = isChecked
        }
    }

    override fun onBackPressed() {
        sendDataBackToPreviousActivity()
        super.onBackPressed()
    }

    private fun sendDataBackToPreviousActivity() {
        MapPrefs.saveSoundToggle(soundSwitchIsChecked)
        val intent = Intent().apply {
            putExtra(CLEF_CLANGED, clefChanged)
            putExtra(SOUND_TOGGLE, soundSwitchIsChecked)
        }
        setResult(RESULT_OK, intent)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        clefSpinner.adapter = adapter
        clefSpinner.setSelection(getClefSelection())
        clefSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if(position == 0) {
                   clefChanged = currentClef == BASS
                   MapPrefs.saveGameClefMode(TREBLE)
               } else {
                   clefChanged = currentClef == TREBLE
                   MapPrefs.saveGameClefMode(BASS)
               }
            }
        }
    }

    private fun getClefSelection(): Int {
        return if (MapPrefs.getGameClefMode() == TREBLE) {
            0
        } else {
            1
        }
    }

}