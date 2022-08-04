package com.example.kotlinretrofitmvvm

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(private val todos: MutableList<Todo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {


    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()

        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = currentItem.title
            cbDone.isChecked = currentItem.isChecked
            toggleStrikeThrough(tvTodoTitle, currentItem.isChecked)
            cbDone.setOnCheckedChangeListener { _, b ->
                toggleStrikeThrough(tvTodoTitle, b)
                currentItem.isChecked = !currentItem.isChecked
            }
        }


    }

    override fun getItemCount(): Int {
        return todos.size
    }


}