package com.sshevtsov.todolist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sshevtsov.todolist.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteListAdapter(
            data = mutableListOf(
                Data(
                    viewType = Data.TYPE_HEADER,
                    headerText = "Notes"
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 1",
                        "Текст заметки 1"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 2",
                        "Текст заметки 2"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 3",
                        "Текст заметки 3"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 4",
                        "Текст заметки 4"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 5",
                        "Текст заметки 5"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 6",
                        "Текст заметки 6"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 7",
                        "Текст заметки 7"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 8",
                        "Текст заметки 8"
                    )
                ),
                Data(
                    viewType = Data.TYPE_NOTE,
                    noteItem = NoteItem(
                        Calendar.getInstance().timeInMillis,
                        "Заголовок 9",
                        "Текст заметки 9"
                    )
                )
            ),
            onNoteItemClickListener = object : NoteListAdapter.OnNoteItemClickListener {
                override fun onEditButtonClicked(data: Data) {
                    Toast.makeText(context, "Edit ${data.noteItem?.title}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onDeleteButtonClicked(data: Data) {
                    Toast.makeText(context, "Delete ${data.noteItem?.title}", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        )

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

}