package com.sshevtsov.todolist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sshevtsov.todolist.databinding.DialogEditNoteBinding

class EditNoteDialog() : DialogFragment() {

    companion object {
        const val NOTE_EXTRA = "NOTE"
        fun newInstance(bundle: Bundle): EditNoteDialog {
            return EditNoteDialog().also {
                it.arguments = bundle
            }
        }
    }

    private var _binding: DialogEditNoteBinding? = null
    private val binding get() = _binding!!

    private val noteItem: NoteItem? by lazy {
        arguments?.getParcelable(NOTE_EXTRA)
    }

    private var callbackOwner: DialogCallback? = null

    fun setCallbackOwner(callbackOwner: DialogCallback) {
        this.callbackOwner = callbackOwner
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.positiveButton.setOnClickListener {
            updateNoteItem()

            callbackOwner?.onPositiveClicked(noteItem!!) ?: throw Exception("Missed callback owner")

            dismiss()
        }

        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun updateNoteItem() {
        noteItem?.let {
            it.title = binding.titleEditText.text.toString()
            it.body = binding.bodyEditText.text.toString()
        } ?: throw Exception("Missed bundle data with NOTE_EXTRA key")
    }

    interface DialogCallback {
        fun onPositiveClicked(noteItem: NoteItem)
    }

}