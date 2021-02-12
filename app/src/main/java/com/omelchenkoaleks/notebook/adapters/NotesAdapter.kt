package com.omelchenkoaleks.notebook.adapters

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omelchenkoaleks.notebook.R
import com.omelchenkoaleks.notebook.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var listener: OnItemClickListener? = null

    private var listNotes = ArrayList<Notes>()

    fun setData(notes: List<Notes>) {
        listNotes = notes as ArrayList<Notes>
    }

    fun setOnClickListener(_listener: OnItemClickListener) {
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tv_item_title.text = listNotes[position].title
        holder.itemView.tv_item_description.text = listNotes[position].textnote
        holder.itemView.tv_item_datetime.text = listNotes[position].datetime

        if (listNotes[position].color != null) {
            holder.itemView.card_view.setCardBackgroundColor(Color.parseColor(listNotes[position].color))
        } else {
            holder.itemView.card_view.setCardBackgroundColor(R.color.light_black)
        }

        if (listNotes[position].imagepath != null) {
            holder.itemView.riv_image_note.setImageBitmap(BitmapFactory.decodeFile(listNotes[position].imagepath))
            holder.itemView.riv_image_note.visibility = View.VISIBLE
        } else {
            holder.itemView.riv_image_note.visibility = View.GONE
        }

        if (listNotes[position].weblink != null) {
            holder.itemView.tv_item_web_link.text = listNotes[position].weblink
            holder.itemView.tv_item_web_link.visibility = View.VISIBLE
        } else {
            holder.itemView.tv_item_web_link.visibility = View.GONE
        }

        holder.itemView.card_view.setOnClickListener {
            listener?.onClicked(listNotes[position].id!!)
        }

    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    interface OnItemClickListener {
        fun onClicked(noteId: Int)
    }

}