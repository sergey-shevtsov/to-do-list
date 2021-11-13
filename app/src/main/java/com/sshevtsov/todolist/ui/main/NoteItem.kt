package com.sshevtsov.todolist.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class NoteItem(
    val timestamp: Long = Calendar.getInstance().timeInMillis,
    var title: String = "",
    var body: String = "",
    var priority: Float = 0f
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteItem

        if (title != other.title) return false
        if (body != other.body) return false
        if (priority != other.priority) return false

        return true
    }

    override fun hashCode(): Int {
        return timestamp.hashCode()
    }
}
