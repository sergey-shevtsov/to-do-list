package com.sshevtsov.todolist.utils

import android.text.Spannable

fun Spannable.getMatchIndices(text: String) : List<Int> {
    if (text.isEmpty()) return emptyList()

    val matches = mutableListOf<Int>()

    var lastIndex = 0

    while (lastIndex != -1) {

        lastIndex = this.indexOf(text, lastIndex, true)

        if (lastIndex != -1) {
            matches.add(lastIndex)
            lastIndex++
        }

    }

    return matches
}