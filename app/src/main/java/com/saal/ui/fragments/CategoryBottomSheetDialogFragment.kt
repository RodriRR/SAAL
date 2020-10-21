package com.saal.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.*
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import android.view.LayoutInflater
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.saal.data.model.Category
import com.saal.databinding.FragmentCategoryBinding
import com.saal.ui.adapters.CategoryAdapter
import com.saal.ui.adapters.CategoryListener
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saal.R
import com.saal.databinding.ItemCreateCategoryBinding

/**
 * Inflate the bottomSheetFragment
 */
class CategoryBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = CategoryBottomSheetDialogFragment()
    }

    private val viewModel by sharedViewModel<MainViewModel>()


    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoryBinding.inflate(inflater)

        setStyle(DialogFragment.STYLE_NO_FRAME, 0)

        //Click on a Category
        val clickListener = CategoryListener {
            dismiss()
            showDialogEdit(it)
        }

        binding.addCategoryEt.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s.toString())
            }

        })

        //Control add button
        binding.addButton.setOnClickListener {
            showDialogCreate()
        }

        viewModel.pruebaCategories.observe(this, Observer {
            var adapter = binding.categoryList.adapter as CategoryAdapter
            adapter.submitList(it)
        })

        //Set up Category adapter
        val adapter = CategoryAdapter(clickListener)
        binding.categoryList.adapter = adapter
        val grid = binding.categoryList.layoutManager as GridLayoutManager
        grid.spanCount = 2
        binding.categoryList.layoutManager = grid

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun showDialogEdit(category: Category) {
        val binding = ItemCreateCategoryBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom)
        builder.setView(binding.root)

        viewModel.nameNewCategory.value = category.name

        binding.viewmodel = viewModel
        builder.setPositiveButton("OK") { _, _ ->
            viewModel.updateCategory(category)
        }
        builder.setNegativeButton("DELETE") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            viewModel.deleteCategory(category)
            dialog.dismiss()
        }
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(context!!.getColor(R.color.delete))
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(context!!.getColor(R.color.edit))

    }

    private fun showdialogError(error: String) {
        MaterialAlertDialogBuilder(context).setTitle("Error").setMessage(error)
            .setPositiveButton("Ok") { _, _ -> }.show()
    }

    private fun showDialogCreate() {
        val binding = ItemCreateCategoryBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom)
        builder.setView(binding.root)

        binding.viewmodel = viewModel
        builder.setPositiveButton("Create") { _, _ ->
            if (viewModel.nameNewCategory.value.isNullOrEmpty() || viewModel.nameNewCategory.value!!.trim() == "") {
                showdialogError("Please enter a correct name")
            } else {
                val category = Category(0, viewModel.nameNewCategory.value!!)
                viewModel.insertNewCategory(category)
            }
        }
        builder.setNegativeButton("Cancel") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            dialog.dismiss()
        }
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY)
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(context!!.getColor(R.color.edit))
    }


}
