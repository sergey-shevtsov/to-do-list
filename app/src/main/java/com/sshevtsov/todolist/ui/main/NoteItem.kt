package com.sshevtsov.todolist.ui.main

import android.os.Parcelable
import android.text.Spannable
import androidx.core.text.toSpannable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class NoteItem(
    val timestamp: Long = Calendar.getInstance().timeInMillis,
    var title: @RawValue Spannable = "".toSpannable(),
    var body: @RawValue Spannable = "".toSpannable(),
    var priority: Float = 0f,
    var hasChanges: Boolean = false
) : Parcelable
