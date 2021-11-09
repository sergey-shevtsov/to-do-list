package com.sshevtsov.todolist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.todolist.R
import com.sshevtsov.todolist.databinding.ListItemHeaderBinding
import com.sshevtsov.todolist.databinding.ListItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteListAdapter(
    private val onNoteItemClickListener: OnNoteItemClickListener,
    private val data: MutableList<Data>
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Data.TYPE_NOTE -> NoteItemViewHolder(
                inflater.inflate(
                    R.layout.list_item_note,
                    parent,
                    false
                )
            )
            else -> HeaderViewHolder(
                inflater.inflate(
                    R.layout.list_item_header,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].viewType

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(data: Data) {
            val binding = ListItemHeaderBinding.bind(itemView)
            binding.headerTitle.text = data.headerText
        }

    }

    inner class NoteItemViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(data: Data) {
            val binding = ListItemNoteBinding.bind(itemView)
            binding.apply {
                textViewTimestamp.text =
                    data.noteItem?.timestamp?.convertToPattern("dd.MM.yy HH:mm")
                textViewHeader.text = data.noteItem?.title
                textViewBody.text = data.noteItem?.body

                editImageButton.setOnClickListener {
                    onNoteItemClickListener.onEditButtonClicked(data, layoutPosition)
                }
                deleteImageButton.setOnClickListener { removeItem() }
            }
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }

    interface OnNoteItemClickListener {
        fun onEditButtonClicked(data: Data, position: Int)
    }
}

fun Long.convertToPattern(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}