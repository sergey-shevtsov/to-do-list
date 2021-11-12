package com.sshevtsov.todolist.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.sshevtsov.todolist.R
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

        generateDataList(15)
        initNoteList()

        binding.fabAdd.setOnClickListener {
            openEditDialogFragment(NoteItem())
        }
    }

    private fun generateDataList(size: Int) {
        data.addAll(NotesExample.getNoteList(size))
    }

    private fun initNoteList() {
        adapter = NoteListAdapter(
            context = requireContext(),
            data = data,
            onNoteItemClickListener = object : NoteListAdapter.OnNoteItemClickListener {
                override fun onEditButtonClicked(data: Data, position: Int) {
                    data.noteItem?.let { note ->
                        openEditDialogFragment(note, position)
                    }
                }
            }
        )

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(binding.recyclerView)
    }

    private fun openEditDialogFragment(noteItem: NoteItem) {
        val bundle = Bundle()
        bundle.putParcelable(
            EditNoteDialog.NOTE_EXTRA,
            noteItem
        )
        val editNoteDialog = EditNoteDialog.newInstance(bundle)
        editNoteDialog.setCallbackOwner(this@MainFragment)
        editNoteDialog.show(parentFragmentManager, null)
    }

    private fun openEditDialogFragment(noteItem: NoteItem, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable(
            EditNoteDialog.NOTE_EXTRA,
            noteItem
        )
        bundle.putInt(
            EditNoteDialog.POSITION_EXTRA,
            position
        )
        val editNoteDialog = EditNoteDialog.newInstance(bundle)
        editNoteDialog.setCallbackOwner(this@MainFragment)
        editNoteDialog.show(parentFragmentManager, null)
    }

    override fun onPositiveClicked(noteItem: NoteItem, position: Int) {
        if (position == -1) {
            data.add(Data(viewType = Data.TYPE_NOTE, noteItem = noteItem))
            adapter.notifyItemInserted(data.size - 1)
        } else {
            data[position].noteItem?.title = noteItem.title
            data[position].noteItem?.body = noteItem.body
            adapter.notifyItemChanged(position)
        }
    }

}