package com.sshevtsov.todolist.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteItem(
    val timestamp: Long,
    var title: String = "",
    var body: String = ""
) : Parcelable
