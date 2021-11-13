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
) : Parcelable
