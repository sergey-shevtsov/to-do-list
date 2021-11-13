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

        return if (oldItems[oldItemPosition].viewType == Data.TYPE_NOTE) {
            oldItems[oldItemPosition].noteItem!!.timestamp ==
                    newItems[newItemPosition].noteItem!!.timestamp
        } else {
            oldItems[oldItemPosition].noteItemSpannable!!.timestamp ==
                    newItems[newItemPosition].noteItemSpannable!!.timestamp
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldItemPosition == 0 && newItemPosition == 0) return true

        val oldItem = oldItems[oldItemPosition].noteItem!!
        val newItem = newItems[newItemPosition].noteItem!!
        val oldItemSpannable = oldItems[oldItemPosition].noteItemSpannable!!
        val newItemSpannable = newItems[newItemPosition].noteItemSpannable!!

        return if (oldItems[oldItemPosition].viewType == Data.TYPE_NOTE) {
            oldItem.title == newItem.title &&
                    oldItem.body == newItem.body &&
                    oldItem.priority == newItem.priority
        } else {
            !newItemSpannable.hasChanges &&
                    oldItemSpannable.title == newItemSpannable.title &&
                    oldItemSpannable.body == newItemSpannable.body &&
                    oldItemSpannable.priority == newItemSpannable.priority
        }
    }
}