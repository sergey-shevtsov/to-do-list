package com.sshevtsov.todolist.ui.main

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.toSpannable
import androidx.fragment.app.DialogFragment
import com.sshevtsov.todolist.R
import com.sshevtsov.todolist.databinding.DialogEditNoteBinding

class EditNoteDialog() : DialogFragment() {

    companion object {
        const val NOTE_EXTRA = "NOTE"
        const val POSITION_EXTRA = "POSITION"
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(POSITION_EXTRA, -1)

        fillInputFields()

        binding.prioritySlider.setLabelFormatter { value ->
            when (value) {
                2f -> requireContext().getString(R.string.priority_high)
                1f -> requireContext().getString(R.string.priority_medium)
                else -> requireContext().getString(R.string.priority_low)
            }
        }

        binding.prioritySlider.addOnChangeListener { _, value, _ ->
            paintPrioritySlider(value)
        }

        binding.positiveButton.setOnClickListener {
            updateNoteItem()

            callbackOwner?.onPositiveClicked(noteItem!!, position!!)
                ?: throw Exception("Missed callback owner")

            dismiss()
        }

        binding.negativeButton.setOnClickListener {
            dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fillInputFields() {
        noteItem?.let { note ->
            binding.titleEditText.text = SpannableStringBuilder(note.title)
            binding.bodyEditText.text = SpannableStringBuilder(note.body)
            binding.prioritySlider.value = note.priority
            paintPrioritySlider(note.priority)
        } ?: throw Exception("Missed bundle data with NOTE_EXTRA key")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun paintPrioritySlider(value: Float) {
        binding.prioritySlider.apply {
            when (value) {
                2f -> {
                    trackActiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_high_priority))
                    trackInactiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_high_priority_24per))
                    thumbTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_high_priority))
                    haloTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_high_priority_24per))
                    tickActiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_high_priority_54per))
                    tickInactiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_high_priority_54per))
                }
                1f -> {
                    trackActiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_medium_priority))
                    trackInactiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_medium_priority_24per))
                    thumbTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_medium_priority))
                    haloTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_medium_priority_24per))
                    tickActiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_medium_priority_54per))
                    tickInactiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_medium_priority_54per))
                }
                else -> {
                    trackActiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_low_priority))
                    trackInactiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_low_priority_24per))
                    thumbTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_low_priority))
                    haloTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_low_priority_24per))
                    tickActiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_low_priority_54per))
                    tickInactiveTintList = ColorStateList.valueOf(requireContext().getColor(R.color.color_low_priority_54per))
                }
            }
        }
    }

    private fun updateNoteItem() {
        noteItem?.let {
            it.title = binding.titleEditText.text.toString().toSpannable()
            it.body = binding.bodyEditText.text.toString().toSpannable()
            it.priority = binding.prioritySlider.value
        } ?: throw Exception("Missed bundle data with NOTE_EXTRA key")
    }

    interface DialogCallback {
        fun onPositiveClicked(noteItem: NoteItem, position: Int)
    }

}