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

class MainFragment : Fragment(), EditNoteDialog.DialogCallback {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val data = mutableListOf(Data(Data.TYPE_HEADER, "Notes"))

    private lateinit var adapter: NoteListAdapter

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

        adapter = NoteListAdapter(
            data = data,
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

        binding.fabAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(
                EditNoteDialog.NOTE_EXTRA,
                NoteItem(Calendar.getInstance().timeInMillis)
            )
            val editNoteDialog = EditNoteDialog.newInstance(bundle)
            editNoteDialog.setCallbackOwner(this)
            editNoteDialog.show(parentFragmentManager, null)
        }
    }

    override fun onPositiveClicked(noteItem: NoteItem) {
        data.add(Data(viewType = Data.TYPE_NOTE, noteItem = noteItem))
        adapter.notifyItemInserted(data.size - 1)
    }

}