package com.omelchenkoaleks.notebook.dao

import androidx.room.*
import com.omelchenkoaleks.notebook.entities.Notes

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes(): List<Notes>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getSpecificNote(id: Int): Notes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

}