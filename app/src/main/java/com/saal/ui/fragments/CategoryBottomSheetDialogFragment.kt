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
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
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

        //Control add button
        binding.addButton.setOnClickListener {
            if (viewModel.nameNewCategory.value.isNullOrEmpty() || viewModel.nameNewCategory.value!!.trim() == "") {
                showdialogError("Please enter a correct name")
            } else {
                viewModel.createNewCategory(0)
            }
            val imm = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }

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


}
