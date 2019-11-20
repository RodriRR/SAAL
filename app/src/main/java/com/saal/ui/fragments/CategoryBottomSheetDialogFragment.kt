package com.saal.ui.fragments

import android.os.Bundle
import android.view.*
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.saal.data.model.Category
import com.saal.databinding.FragmentCategoryBinding
import com.saal.ui.adapters.CategoryAdapter
import com.saal.ui.adapters.CategoryListener
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class CategoryBottomSheetDialogFragment : BottomSheetDialogFragment {

    constructor()
    companion object{
        fun newInstance() = CategoryBottomSheetDialogFragment()
    }

    private val viewModel by sharedViewModel<MainViewModel>()


    @Nullable
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoryBinding.inflate(inflater)


        val clickListener = CategoryListener { category : Category, type : Int, editText : View, editButton : View ->
            when(type){
                //Edit
                1 -> {
                    //Focus EditText
                    var editTextView = editText as EditText
                    val pos = editTextView.text.length
                    editTextView.setSelection(pos)
                    editTextView.requestFocus()
                    val imm = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm!!.showSoftInput(editTextView, SHOW_IMPLICIT)
                    //Edit button
                    editButton.visibility = View.VISIBLE
                    editButton.setOnClickListener { vew : View ->
                        viewModel.nameNewCategory.value = editTextView.text.toString()
                        viewModel.createNewCategory(category.id)
                        editButton.visibility = View.INVISIBLE
                    }
                }
                //Remove
                2 -> viewModel.deleteCategory(category)
            }
        }
        val adapter = CategoryAdapter(clickListener)
        binding.categoryList.adapter = adapter

        binding.viewmodel = viewModel

        binding.lifecycleOwner = this
        return binding.root
    }


}
