package com.example.notetodoapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetodoapp.R
import com.example.notetodoapp.databinding.FragmentTodoBinding
import com.example.notetodoapp.model.dc.Todo
import com.example.notetodoapp.viewmodel.NoteViewModel
import com.example.notetodoapp.views.adapter.OnTodoClick
import com.example.notetodoapp.views.adapter.OnTodoClickDelete
import com.example.notetodoapp.views.adapter.TodoRVAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class TodoFragment : Fragment(), OnTodoClick, OnTodoClickDelete {
    private lateinit var binding: FragmentTodoBinding
    private lateinit var rvTodo: RecyclerView
    private lateinit var todoViewModel: NoteViewModel
    private lateinit var adapter: TodoRVAdapter
    private lateinit var dialog: BottomSheetDialog
    private lateinit var displayBottomSheet: View
    private lateinit var inputTextTask: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = activity

        val fabTodoAdd = binding.fabAddTodo
        displayBottomSheet = layoutInflater.inflate(R.layout.fragment_todobottomsheet, null)
        dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(displayBottomSheet)

        val todoMsg = displayBottomSheet.findViewById<TextView>(R.id.etEnterTask)



        rvTodo = binding.rvTodoList
        rvTodo.layoutManager = LinearLayoutManager(requireContext())
        adapter = TodoRVAdapter(requireContext(), this, this)
        rvTodo.adapter = adapter

        todoViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(context!!.application)).get(NoteViewModel::class.java)

        todoViewModel.allTodo.observe(context, Observer { list ->
            list.let {
                adapter.updateTodo(it)
            }
        })

        fabTodoAdd.setOnClickListener {
            showBottomSheet()
        }

    }


    private fun showBottomSheet() {
        inputTextTask = displayBottomSheet.findViewById(R.id.etEnterTask)
        val btnTodoDone = displayBottomSheet.findViewById<Button>(R.id.btnTodoDone)
        btnTodoDone.requestFocus()

        btnTodoDone.setOnClickListener {
            handleTodoButtonClick()
        }

        dialog.show()
    }

    private fun handleTodoButtonClick() {
        val todoMsg = displayBottomSheet.findViewById<TextView>(R.id.etEnterTask)

        val task = inputTextTask.text.toString()

        if (task.isNotEmpty()) {
            todoViewModel.insertTodo(Todo(task, false))
            Toast.makeText(requireContext(), "Task Saved", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

            todoMsg.text = ""

        } else {
            Toast.makeText(requireContext(), "Field is empty", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    override fun onTodoClick(todo: Todo) {
        val todoModel = Todo(task = todo.task, isChecked = !todo.isChecked)
        todoModel.id = todo.id
        todoViewModel.updateTodo(todoModel)
        adapter.notifyDataSetChanged()
    }

    override fun onLongPressDelete(todo: Todo) {
        todoViewModel.deleteTodo(todo)
        Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_SHORT).show()
    }


}