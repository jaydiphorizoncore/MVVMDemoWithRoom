package com.example.mvvmdemowithroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemowithroom.adapter.NoteAdapter
import com.example.mvvmdemowithroom.adapter.NoteClickDeleteInterface
import com.example.mvvmdemowithroom.adapter.NoteClickInterface
import com.example.mvvmdemowithroom.database.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
     var viewModal: NoteViewModal? = null
    lateinit var noteRecyclerView: RecyclerView
    lateinit var fbAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this, this)
        noteRecyclerView.adapter = noteAdapter

        // on below line we are
        // initializing our view modal.

        viewModal = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModal::class.java]

        viewModal!!.allNotes.observe(this, Observer { list ->
            list?.let {
                noteAdapter.updateList(it)
            }
        })

        fbAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    private fun initView() {
        noteRecyclerView = findViewById(R.id.notesRecyclerView)
        fbAdd = findViewById(R.id.fbAdd)
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        viewModal?.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}

