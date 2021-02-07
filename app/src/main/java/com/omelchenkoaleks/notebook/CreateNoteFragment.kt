package com.omelchenkoaleks.notebook

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.omelchenkoaleks.notebook.database.NotesDatabase
import com.omelchenkoaleks.notebook.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    var currentDate: String? = null

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

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        tv_datetime.text = currentDate

        image_done.setOnClickListener {
            saveNote()
        }

        image_back.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
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

}