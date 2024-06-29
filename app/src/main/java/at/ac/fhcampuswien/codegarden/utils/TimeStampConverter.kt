package at.ac.fhcampuswien.codegarden.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun convertTimestamp(timestamp: String): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val date = inputFormat.parse(timestamp)
    return date?.let { outputFormat.format(it) }
}