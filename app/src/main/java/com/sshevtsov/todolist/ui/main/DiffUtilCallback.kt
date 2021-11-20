package com.sshevtsov.todolist.ui.main

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldItems: List<Data>,
    private val newItems: List<Data>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldItemPosition == 0 || newItemPosition == 0) {
            return oldItemPosition == newItemPosition
        }

        return oldItems[oldItemPosition].noteItem!!.timestamp ==
                newItems[newItemPosition].noteItem!!.timestamp
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldItemPosition == 0 && newItemPosition == 0) return true
        return !newItems[newItemPosition].noteItem!!.hasChanges
    }
}