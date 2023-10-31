package com.example.notetodoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notetodoapp.model.dc.Note
import com.example.notetodoapp.model.db.NoteDatabase
import com.example.notetodoapp.model.repo.NotesRepository
import com.example.notetodoapp.model.dc.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    val allTodo: LiveData<List<Todo>>
    private val repository: NotesRepository


    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        val todoDao = NoteDatabase.getDatabase(application).getTodoDao()
        repository = NotesRepository(dao, todoDao)
        allNotes = repository.getAllNotes
        allTodo = repository.getAllTodo
    }


    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }


    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }


    // todoViewModel operations

    fun insertTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTodo(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTodo(todo)
    }


}