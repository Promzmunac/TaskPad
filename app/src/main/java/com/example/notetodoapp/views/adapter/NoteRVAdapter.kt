package com.example.notetodoapp.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notetodoapp.databinding.NoteRvItemBinding
import com.example.notetodoapp.model.dc.Note

class NoteRVAdapter(
    val context: Context, private val noteClickDeleteInterface: NoteClickDeleteInterface,
    private val noteClickInterface: NoteClickInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    // variable for our all notes list.
    private val allNotes = ArrayList<Note>()

    //create the view holder class.
    inner class ViewHolder(binding: NoteRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val noteTV = binding.idTVNote
        val dateTV = binding.idTVDate
        val deleteIV = binding.idIVDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layout = NoteRvItemBinding.inflate( LayoutInflater.from(parent.context),
            parent, false)

        return ViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteHolder = allNotes[position]
        holder.noteTV.text = noteHolder.title
        holder.dateTV.text = "time: ${noteHolder.timestamp}"

        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(noteHolder)
        }
        //click to edit
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(noteHolder)
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    fun onNoteClick(note: Note)
}