package com.sshevtsov.todolist.ui.main

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sshevtsov.todolist.R
import com.sshevtsov.todolist.databinding.ListItemHeaderBinding
import com.sshevtsov.todolist.databinding.ListItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteListAdapter(
    private val context: Context,
    private val onNoteItemClickListener: OnNoteItemClickListener,
    private val data: MutableList<Data>
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (toPosition != 0) {
            data.removeAt(fromPosition).apply {
                data.add(toPosition, this)
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    fun setData(newData: List<Data>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newData))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(data: Data) {
            val binding = ListItemHeaderBinding.bind(itemView)
            binding.headerTitle.text = data.headerText
        }

    }

    inner class NoteItemViewHolder(itemView: View) : BaseViewHolder(itemView),
        ItemTouchHelperViewHolder {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun bind(data: Data) {
            val binding = ListItemNoteBinding.bind(itemView)
            binding.apply {
                textViewTimestamp.text =
                    data.noteItem?.timestamp?.convertToPattern(context.getString(R.string.timestamp_draw_pattern))
                textViewHeader.text = data.noteItem?.title
                textViewBody.text = data.noteItem?.body
                val priority = when (data.noteItem?.priority) {
                    2f -> context.getString(R.string.priority_high)
                    1f -> context.getString(R.string.priority_medium)
                    else -> context.getString(R.string.priority_low)
                }
                textViewPriority.text = String.format(
                    Locale.getDefault(),
                    context.getString(R.string.note_item_priority_draw_pattern),
                    context.getString(R.string.note_item_priority_title),
                    priority
                )
                textViewPriority.setTextColor(
                    context.getColor(
                        when (data.noteItem?.priority) {
                            2f -> R.color.color_high_priority_54per
                            1f -> R.color.color_medium_priority_54per
                            else -> R.color.color_low_priority_54per
                        }
                    )
                )

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

        override fun onItemSelected() {
            itemView.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .duration = 200
        }

        override fun onItemClear() {
            itemView.animate()
                .scaleX(1f)
                .scaleY(1f)
                .duration = 200
        }
    }

    fun interface OnNoteItemClickListener {
        fun onEditButtonClicked(data: Data, position: Int)
    }
}

fun Long.convertToPattern(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}