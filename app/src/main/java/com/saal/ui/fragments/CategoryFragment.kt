package com.saal.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.saal.databinding.FragmentCategoryBinding
import com.saal.ui.adapters.CategoryAdapter
import com.saal.ui.adapters.CategoryListener
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class CategoryFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCategoryBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        val clickListener = CategoryListener {
            Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
        }
        val adapter = CategoryAdapter(clickListener)
        binding.categoryList.adapter = adapter
        var grid = binding.categoryList.layoutManager as GridLayoutManager
        grid.spanCount = 2
        binding.categoryList.layoutManager = grid

        binding.viewmodel = viewModel

        binding.lifecycleOwner = this
        return binding.root
    }
}
