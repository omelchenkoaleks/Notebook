package com.omelchenkoaleks.notebook

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.omelchenkoaleks.notebook.database.NotesDatabase
import com.omelchenkoaleks.notebook.entities.Notes
import com.omelchenkoaleks.notebook.utils.NoteBottomSheerFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    var currentDate: String? = null
    var selectedColor = "#171C26"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        fun newInstance() =
            CreateNoteFragment()
//                .apply {
//                arguments = Bundle().apply {
//                }
//            }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        tv_datetime.text = currentDate
        color_view.setBackgroundColor(Color.parseColor(selectedColor))

        image_done.setOnClickListener {
            saveNote()
        }

        image_back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        image_more.setOnClickListener {
            val noteBottomSheerFragment = NoteBottomSheerFragment.newInstance()
            noteBottomSheerFragment.show(
                requireActivity().supportFragmentManager,
                "Note Bottom Sheet Fragment"
            )
        }
    }

    private fun saveNote() {

        if (et_note_title.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }

        if (et_note_subtitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Subtitle is Required", Toast.LENGTH_SHORT).show()
        }

        if (et_note_description.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        }


        launch {
            val notes = Notes()
            notes.title = et_note_title.text.toString()
            notes.subtitle = et_note_subtitle.text.toString()
            notes.textnote = et_note_description.text.toString()
            notes.datetime = currentDate
            notes.color = selectedColor
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                et_note_title.setText("")
                et_note_subtitle.setText("")
                et_note_description.setText("")
            }
        }


    }

    private fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }

        fragmentTransition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit() // TODO: I'm added? Is correctly?
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val actionColor = intent!!.getStringExtra("actionColor")

            when (actionColor!!) {

                "Blue" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }
//                "White" -> {
//                    selectedColor = intent.getStringExtra("selectedColor")!!
//                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
//                }
                "Purple" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Green" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Black" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Orange" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }

                else -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    color_view.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

}