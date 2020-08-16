package com.example.playthatnotes.helpers

import java.lang.Enum

enum class Note {
    C5, D5, E5, F5, G5, A5, B5, C6, D6, E6, F6, G6, A6, B6
}

enum class Clef {
    TREBLE, BASS;

    open fun toMyEnum(myEnumString: String?): Clef {
        return try {
            Clef.valueOf(myEnumString ?: "")
        } catch (ex: Exception) {
            // For error cases
            TREBLE
        }
    }
}