package com.sshevtsov.todolist.ui.main

data class Data(
    val viewType: Int,
    val headerText: String? = null,
    val noteItem: NoteItem? = null
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NOTE = 1
    }
}
