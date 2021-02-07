package com.omelchenkoaleks.notebook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omelchenkoaleks.notebook.R
import com.omelchenkoaleks.notebook.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter(private val listNotes: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.tv_item_title.text = listNotes[position].title
        holder.itemView.tv_item_description.text = listNotes[position].textnote
        holder.itemView.tv_item_datetime.text = listNotes[position].datetime
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

}