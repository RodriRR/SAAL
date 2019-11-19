package com.saal.ui.fragments

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

        val clickListener = CategoryListener { category : Category, view : Int ->
            when(view){
                1 -> {}
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
