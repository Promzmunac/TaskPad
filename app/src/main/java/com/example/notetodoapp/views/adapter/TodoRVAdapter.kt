package com.example.notetodoapp.views.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notetodoapp.databinding.TodoRvItemBinding
import com.example.notetodoapp.model.dc.Todo

class TodoRVAdapter(
    val context: Context,
    private val onTodoClickInterface: OnTodoClick,
    private val onTodoClickDelete: OnTodoClickDelete
)
    : RecyclerView.Adapter<TodoRVAdapter.ViewHolder>() {

    private val allTodo = ArrayList<Todo>()

    inner class ViewHolder(binding: TodoRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val task = binding.tvTodo
        val cbTodo = binding.cbTodo
        val deleteBtn = binding.btnTodoCancel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = TodoRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val todoPos = allTodo[position]
        holder.task.text = todoPos.task

        holder.deleteBtn.setOnClickListener {
            onTodoClickDelete.onLongPressDelete(todo = todoPos)
        }

        holder.cbTodo.setOnClickListener {
            onTodoClickInterface.onTodoClick(todoPos)
        }

        if (todoPos.isChecked) {
            holder.cbTodo.isChecked = true
            holder.cbTodo.jumpDrawablesToCurrentState()
            holder.task.paintFlags = holder.task.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.task.setTextColor(Color.RED)
        } else {
            holder.cbTodo.isChecked = false
            holder.cbTodo.jumpDrawablesToCurrentState()
            holder.task.setTextColor(Color.BLACK)
            holder.task.paintFlags = 0
        }

    }

    override fun getItemCount(): Int {
        return allTodo.size
    }

    fun updateTodo(newList: List<Todo>) {
        allTodo.clear()
        allTodo.addAll(newList)
        notifyDataSetChanged()
    }
}

interface OnTodoClick {
    fun onTodoClick(todo: Todo)
}

interface OnTodoClickDelete {
    fun onLongPressDelete(todo: Todo)
}
