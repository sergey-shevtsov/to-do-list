package com.sshevtsov.todolist.ui.main

import android.text.Spannable

data class NoteItemSpannable(
    val timestamp: Long,
    var title: Spannable,
    var body: Spannable,
    var priority: Float,
    var hasChanges: Boolean = false
)
