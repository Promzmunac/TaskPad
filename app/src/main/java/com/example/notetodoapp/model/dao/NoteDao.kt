package com.example.notetodoapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notetodoapp.model.dc.Note


@Dao
interface NoteDao {

    //insert delete update getAllNotes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

}