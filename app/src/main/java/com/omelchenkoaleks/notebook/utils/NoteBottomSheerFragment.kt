package com.omelchenkoaleks.notebook.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.omelchenkoaleks.notebook.R
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*

class NoteBottomSheerFragment : BottomSheetDialogFragment() {

    var selectorColor = "#171C26"

    companion object {
        fun newInstance(): NoteBottomSheerFragment {
            val args = Bundle()
            val fragment = NoteBottomSheerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior

        if (behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {

        fl_note_blue.setOnClickListener {

            img_note_blue.setImageResource(R.drawable.ic_tick_white)
            img_note_yellow.setImageResource(0)
//            img_note_white.setImageResource(0)
            img_note_purple.setImageResource(0)
            img_note_green.setImageResource(0)
            img_note_black.setImageResource(0)
            img_note_orange.setImageResource(0)
            selectorColor = "#4e33ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selectedColor", selectorColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fl_note_yellow.setOnClickListener {

            img_note_blue.setImageResource(0)
            img_note_yellow.setImageResource(R.drawable.ic_tick_white)
//            img_note_white.setImageResource(0)
            img_note_purple.setImageResource(0)
            img_note_green.setImageResource(0)
            img_note_black.setImageResource(0)
            img_note_orange.setImageResource(0)
            selectorColor = "#ffd633"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Yellow")
            intent.putExtra("selectedColor", selectorColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

//        fl_note_white.setOnClickListener {
//
//        img_note_blue.setImageResource(0)
//        img_note_yellow.setImageResource(0)
//        img_note_white.setImageResource(R.drawable.ic_tick_black)
//        img_note_purple.setImageResource(0)
//        img_note_green.setImageResource(0)
//        img_note_black.setImageResource(0)
//        img_note_orange.setImageResource(0)
//        selectorColor = "#ffffff"
//
//        val intent = Intent("bottom_sheet_action")
//        intent.putExtra("action", "White")
//        intent.putExtra("selectedColor", selectorColor)
//        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
//        }

        fl_note_purple.setOnClickListener {

            img_note_blue.setImageResource(0)
            img_note_yellow.setImageResource(0)
//            img_note_white.setImageResource(0)
            img_note_purple.setImageResource(R.drawable.ic_tick_white)
            img_note_green.setImageResource(0)
            img_note_black.setImageResource(0)
            img_note_orange.setImageResource(0)
            selectorColor = "#ae3b76"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Purple")
            intent.putExtra("selectedColor", selectorColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fl_note_green.setOnClickListener {

            img_note_blue.setImageResource(0)
            img_note_yellow.setImageResource(0)
//            img_note_white.setImageResource(0)
            img_note_purple.setImageResource(0)
            img_note_green.setImageResource(R.drawable.ic_tick_white)
            img_note_black.setImageResource(0)
            img_note_orange.setImageResource(0)
            selectorColor = "#52C32D"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Green")
            intent.putExtra("selectedColor", selectorColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fl_note_black.setOnClickListener {

            img_note_blue.setImageResource(0)
            img_note_yellow.setImageResource(0)
//            img_note_white.setImageResource(0)
            img_note_purple.setImageResource(0)
            img_note_green.setImageResource(0)
            img_note_black.setImageResource(R.drawable.ic_tick_white)
            img_note_orange.setImageResource(0)
            selectorColor = "#202734"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Black")
            intent.putExtra("selectedColor", selectorColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        fl_note_orange.setOnClickListener {

            img_note_blue.setImageResource(0)
            img_note_yellow.setImageResource(0)
//            img_note_white.setImageResource(0)
            img_note_purple.setImageResource(0)
            img_note_green.setImageResource(0)
            img_note_black.setImageResource(0)
            img_note_orange.setImageResource(R.drawable.ic_tick_white)
            selectorColor = "#ff7746"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Orange")
            intent.putExtra("selectedColor", selectorColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        li_image.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        li_bottom_sheet_web_url.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "WebUrl")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }


    }
}