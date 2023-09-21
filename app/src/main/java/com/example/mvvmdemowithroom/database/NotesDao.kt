package com.example.mvvmdemowithroom.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    // adding a new entry to our database.
    @Insert
    suspend fun insert(note: Note)

    // for deleting our note.
    @Delete
    suspend fun delete(note: Note)

    // we have to get the data.
    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    // below method is use to update the note.
    @Update
    suspend fun update(note: Note)
}

