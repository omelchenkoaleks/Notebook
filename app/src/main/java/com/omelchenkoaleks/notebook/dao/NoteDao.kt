package com.omelchenkoaleks.notebook.dao

import androidx.room.*
import com.omelchenkoaleks.notebook.entities.Notes

@Dao
interface NoteDao {

    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes: List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(note: Notes)

    @Delete
    fun deleteNote(note: Notes)

}