package com.sshevtsov.todolist.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.sshevtsov.todolist.R
import com.sshevtsov.todolist.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment(), EditNoteDialog.DialogCallback {

    companion object {
        private const val DATE_DOWN = 0
        private const val DATE_UP = 1
        private const val PRIORITY_DOWN = 2
        private const val PRIORITY_UP = 3
        private const val PRIORITY_NONE = 4
    }

    private var currentSortMethod = Pair(DATE_DOWN, PRIORITY_NONE)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var data = mutableListOf(Data(Data.TYPE_HEADER, "Notes"))

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

        renderSortChipGroup()
        initSortChipGroupListeners()
        sortData()

        initNoteList()

        binding.fabAdd.setOnClickListener {
            openEditDialogFragment(NoteItem())
        }


    }

    private fun sortData() {
        val sortedData = data.toMutableList()
        sortedData.removeAt(0)

        if (currentSortMethod.first == DATE_DOWN) {
            sortedData.sortWith { data1, data2 ->
                if (data1.noteItem!!.timestamp > data2.noteItem!!.timestamp) return@sortWith -1
                else return@sortWith 1
            }
        } else {
            sortedData.sortWith { data1, data2 ->
                if (data1.noteItem!!.timestamp > data2.noteItem!!.timestamp) return@sortWith 1
                else return@sortWith -1
            }
        }

        when (currentSortMethod.second) {
            PRIORITY_DOWN -> {
                sortedData.sortWith { data1, data2 ->
                    return@sortWith (data2.noteItem!!.priority - data1.noteItem!!.priority).toInt()
                }
            }
            PRIORITY_UP -> {
                sortedData.sortWith { data1, data2 ->
                    return@sortWith (data1.noteItem!!.priority - data2.noteItem!!.priority).toInt()
                }
            }
        }

        sortedData.add(0, data[0])
        data = sortedData
    }

    private fun initSortChipGroupListeners() {
        binding.chipSortByDate.setOnClickListener {
            currentSortMethod = Pair(
                if (currentSortMethod.first == DATE_DOWN) DATE_UP
                else DATE_DOWN,
                currentSortMethod.second
            )
            renderSortChipGroup()
            sortData()
            adapter.setData(data)
        }

        binding.chipSortByPriority.setOnClickListener {
            currentSortMethod = Pair(
                currentSortMethod.first,
                when (currentSortMethod.second) {
                    PRIORITY_NONE -> PRIORITY_DOWN
                    PRIORITY_DOWN -> PRIORITY_UP
                    PRIORITY_UP -> PRIORITY_NONE
                    else -> PRIORITY_NONE
                }
            )
            renderSortChipGroup()
            sortData()
            adapter.setData(data)
        }
    }

    private fun renderSortChipGroup() {
        binding.apply {
            when (currentSortMethod.second) {
                PRIORITY_NONE -> {
                    chipSortByPriority.chipIcon = null
                }
                PRIORITY_DOWN -> {
                    chipSortByPriority.chipIcon =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_down)
                }
                PRIORITY_UP -> {
                    chipSortByPriority.chipIcon =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_up)
                }
            }

            when (currentSortMethod.first) {
                DATE_DOWN -> {
                    chipSortByDate.chipIcon =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_down)
                }
                DATE_UP -> {
                    chipSortByDate.chipIcon =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_up)
                }
            }
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