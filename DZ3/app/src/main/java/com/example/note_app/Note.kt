package com.example.note_app

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class Note(
    val id: Int,
    val title: String,
    val description: String,
    val date: LocalDate
) {

    fun getDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        return date.format(formatter)
    }


}