package com.saal.ui.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saal.data.model.Category

@BindingAdapter("setListData")
fun setListData(view: RecyclerView, categories : List<Category>?) {
        var adapter = view.adapter as CategoryAdapter
        adapter.submitList(categories)
}