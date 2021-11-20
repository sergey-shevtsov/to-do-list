package com.sshevtsov.todolist.ui.main

import androidx.core.text.toSpannable
import java.util.*
import kotlin.random.Random

object NotesExample {

    private var dataSize = 0

    fun getNoteList(size: Int): List<Data> {
        val data = mutableListOf<Data>()

        for (i in 0 until size) {
            val noteData = createNoteData()
            data.add(noteData)
            dataSize++
        }

        dataSize = 0

        return data
    }

    private fun createNoteData(): Data {
        val calendarFrom = Calendar.getInstance().apply {
            this.add(Calendar.MONTH, -2)
        }

        val calendarUntil = Calendar.getInstance()

        return Data(
            viewType = Data.TYPE_NOTE,
            noteItem = NoteItem(
                timestamp = Random.nextLong(calendarFrom.timeInMillis, calendarUntil.timeInMillis),
                title = "Title ${dataSize + 1}".toSpannable(),
                body = "Some very important note number ${dataSize + 1} text".toSpannable(),
                priority = Random.nextInt(0, 3).toFloat()
            )
        )
    }

}