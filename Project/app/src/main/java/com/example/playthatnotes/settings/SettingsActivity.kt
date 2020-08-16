package com.example.playthatnotes.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.playthatnotes.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var items = arrayOf("Trebble", "Bass")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupSpinner()
        swichSound.isChecked = true
        swichSound.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Switch1:ON" else "Switch1:OFF"
            Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        clefSpinner.adapter = adapter
        clefSpinner.setSelection(1)
        clefSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(parent?.context,
                    "OnItemSelectedListener : " + position.toString(),
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

}