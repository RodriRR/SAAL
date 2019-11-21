package com.saal.ui.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saal.data.model.Category
import com.saal.data.model.Task

/**
 * Binding data to ui components
 */
@BindingAdapter("setListDataCategory")
fun setListDataCategory(view: RecyclerView, categories: List<Category>?) {
    val adapter = view.adapter as CategoryAdapter
    adapter.submitList(categories)
}

@BindingAdapter("setListDataTask")
fun setListDataTask(view: RecyclerView, task: List<Task>?) {
    val adapter = view.adapter as TasksAdapter
    adapter.submitList(task)
}

@BindingAdapter("setListDataCategoryAddTask")
fun setListDataCategoryAddTask(view: RecyclerView, task: List<Category>?) {
    val adapter = view.adapter as CategoryCreateTaskAdapter
    adapter.submitList(task)
}
