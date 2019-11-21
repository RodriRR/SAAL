package com.saal.ui.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.saal.data.model.Category
import com.saal.data.model.Task

@BindingAdapter("setListDataCategory")
fun setListDataCategory(view: RecyclerView, categories: List<Category>?) {
    var adapter = view.adapter as CategoryAdapter
    adapter.submitList(categories)
}

@BindingAdapter("setListDataTask")
fun setListDataTask(view: RecyclerView, task: List<Task>?) {
    var adapter = view.adapter as TasksAdapter
    adapter.submitList(task)
}

@BindingAdapter("setListDataCategoryAddTask")
fun setListDataCategoryAddTask(view: RecyclerView, task: List<Category>?) {
    var adapter = view.adapter as CategoryCreateTaskAdapter
    adapter.submitList(task)
}

@BindingAdapter("setInt")
fun setInt(view: TextView, int: Int) {
    view.text = int.toString()
}