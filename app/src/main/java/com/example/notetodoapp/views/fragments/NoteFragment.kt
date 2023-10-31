package com.example.notetodoapp.views.fragments

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetodoapp.model.dc.Note
import com.example.notetodoapp.utils.swipeToDelete
import com.example.notetodoapp.utils.swipeToEdit
import com.example.notetodoapp.viewmodel.NoteViewModel
import com.example.notetodoapp.views.AddEditNoteActivity
import com.example.notetodoapp.databinding.FragmentNoteBinding
import com.example.notetodoapp.views.adapter.NoteClickDeleteInterface
import com.example.notetodoapp.views.adapter.NoteClickInterface
import com.example.notetodoapp.views.adapter.NoteRVAdapter

class NoteFragment : Fragment(), NoteClickInterface, NoteClickDeleteInterface {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private var allNotes = emptyList<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         // Inflate the layout for this fragment
          binding = FragmentNoteBinding.inflate(inflater,container,false)
          return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val notesRV = binding.notesRV
        val addFAB = binding.idFAB
        val noteRVAdapter = NoteRVAdapter(requireContext(), this, this)

        notesRV.layoutManager = LinearLayoutManager(requireContext())
        notesRV.adapter = noteRVAdapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(context?.applicationContext as Application)
        )[NoteViewModel::class.java]

        viewModel.allNotes.observe(requireActivity(), Observer { list ->
            list?.let {
                // on below line we are updating our list.
                noteRVAdapter.updateList(it)
                allNotes = list
            }
        })

        addFAB.setOnClickListener {
            val intent = Intent(requireContext(), AddEditNoteActivity::class.java)
            startActivity(intent)
        }

        // Set up swipe-to-delete and swipe-to-edit functionality
        setUpSwipeToDelete()
        setUpSwipeToEdit()
    }

    private fun setUpSwipeToDelete() {
        val swipeToDelete = object : swipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                myDelete(position)
                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelperDelete = ItemTouchHelper(swipeToDelete)
        itemTouchHelperDelete.attachToRecyclerView(binding.notesRV)
    }

    private fun setUpSwipeToEdit() {
        val swipeToEdit = object : swipeToEdit() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                toEdit(position)
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(requireContext(), "Edited", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelperEdit = ItemTouchHelper(swipeToEdit)
        itemTouchHelperEdit.attachToRecyclerView(binding.notesRV)
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(requireContext(), AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteDescription", note.desc)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    fun toEdit(position: Int ){
        val noteHolder = allNotes[position]
        val intent = Intent(requireContext(), AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", noteHolder.title)
        intent.putExtra("noteDescription", noteHolder.desc)
        intent.putExtra("noteId", noteHolder.id)
        startActivity(intent)
    }

    private fun myDelete(position: Int) {
        val noteHolder = allNotes[position]
        viewModel.delete(noteHolder)
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.delete(note)
        // display a toast message
        Toast.makeText(requireContext(), "${note.title} Deleted", Toast.LENGTH_LONG).show()
    }
}