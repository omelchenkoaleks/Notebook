package com.omelchenkoaleks.notebook

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.omelchenkoaleks.notebook.database.NotesDatabase
import com.omelchenkoaleks.notebook.entities.Notes
import com.omelchenkoaleks.notebook.utils.NoteBottomSheerFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.item_rv_notes.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*

private const val READ_STORAGE_PERM = 123
private const val REQUEST_CODE_IMAGE = 456

class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    private var currentDate: String? = null
    var selectedColor = "#171C26"
    var selectedImagePath = ""
    private var webLink = ""
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = requireArguments().getInt("noteId", -1)
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
                .apply {
                arguments = Bundle().apply {
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (noteId != -1) {
            launch {
                context?.let {
                    val note = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                    color_view.setBackgroundColor(Color.parseColor(note.color))
                    et_note_title.setText(note.title)
                    et_note_subtitle.setText(note.subtitle)
                    et_note_description.setText(note.textnote)
                    if (note.imagepath != "") {
                        image_note.setImageBitmap(BitmapFactory.decodeFile(note.imagepath))
                        image_note.visibility = View.VISIBLE
                    } else {
                        image_note.visibility = View.GONE
                    }

                    if (note.weblink != "") {
                        tv_web_link.text = note.weblink
                        tv_web_link.visibility = View.VISIBLE
                    } else {
                        tv_web_link.visibility = View.GONE
                    }
                }
            }

        }

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

        btn_ok.setOnClickListener {
            if (et_web_link.text.toString().trim().isNotEmpty()) {
                checkWebUrl()
            } else {
                Toast.makeText(requireContext(), "Url is Required", Toast.LENGTH_SHORT).show()
            }
        }

        btn_cancel.setOnClickListener {
            li_note_web_url.visibility = View.GONE
        }

        tv_web_link.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(et_web_link.text.toString()))
            startActivity(intent)
        }
    }

    private fun saveNote() {

        if (et_note_title.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        } else if (et_note_subtitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Subtitle is Required", Toast.LENGTH_SHORT).show()
        } else if (et_note_description.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        } else {

            launch {
                val notes = Notes()
                notes.title = et_note_title.text.toString()
                notes.subtitle = et_note_subtitle.text.toString()
                notes.textnote = et_note_description.text.toString()
                notes.datetime = currentDate
                notes.color = selectedColor
                notes.imagepath = selectedImagePath
                notes.weblink = webLink
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    et_note_title.setText("")
                    et_note_subtitle.setText("")
                    et_note_description.setText("")
                    image_note.visibility = View.GONE
                    tv_web_link.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun checkWebUrl() {
        if (Patterns.WEB_URL.matcher(et_web_link.text.toString()).matches()) {
            li_note_web_url.visibility = View.GONE
            et_web_link.isEnabled = false
            webLink = et_web_link.text.toString()
            tv_web_link.visibility = View.VISIBLE
            tv_web_link.text = et_web_link.text.toString()
        } else {
            Toast.makeText(requireContext(), "Url is not valid", Toast.LENGTH_SHORT).show()
        }
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            val actionColor = intent!!.getStringExtra("action")

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

                "Image" -> {
                    readStorageTask()
                    li_note_web_url.visibility = View.GONE
                }

                "WebUrl" -> {
                    li_note_web_url.visibility = View.VISIBLE

                }

                else -> {
                    image_note.visibility = View.GONE
                    li_note_web_url.visibility = View.GONE
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

    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePerm()) {
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permission_text),
                READ_STORAGE_PERM,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun pickImageFromGallery() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath: String? = null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                var selectedImageUrl = data.data
                if (selectedImageUrl != null) {
                    try {
                        var inputStream =
                            requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        image_note.setImageBitmap(bitmap)
                        image_note.visibility = View.VISIBLE

                        selectedImagePath = getPathFromUri(selectedImageUrl)!!

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }

}