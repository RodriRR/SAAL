package com.saal.ui.fragments


import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saal.R
import com.saal.data.model.Category
import com.saal.data.model.Task
import com.saal.databinding.FragmentSearchBinding
import com.saal.databinding.ItemCreateTaskBinding
import com.saal.ui.adapters.CategoryCreateTaskAdapter
import com.saal.ui.adapters.CategoryListener
import com.saal.ui.adapters.TasksAdapter
import com.saal.ui.adapters.TasksListener


/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class SearchFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBinding.inflate(inflater)

        val COUNTRIES = viewModel.prueba()

        val searchAdapter = ArrayAdapter(
            context,
            com.saal.R.layout.dropdown_menu_popup_item,
            COUNTRIES
        )

        val editTextFilledExposedDropdown = binding.filledExposedDropdown
        editTextFilledExposedDropdown.setAdapter(searchAdapter)

        //Task Listener
        val adapter = TasksAdapter(TasksListener { task : Task, view : Int ->
            when(view){
                1 -> {
                    viewModel.clearEditTexts()
                    showDialogEdit(task)
                }
                2 -> viewModel.deleteTask(task)
            }
        })

        viewModel.searchText.observe(this, Observer {
            viewModel.getPrueba()
        })

        binding.taskList.adapter = adapter
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    private fun showDialogEdit(task: Task){
        val binding =  ItemCreateTaskBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom)
        builder.setView(binding.root)

        val adapter = CategoryCreateTaskAdapter(CategoryListener{ category : Category ->
            viewModel.categorySelected.value = category
            Toast.makeText(context,"You have selected ${category.name}", Toast.LENGTH_LONG).show()
        })
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.titleNewTask.value = task.title
        viewModel.descriptionNewTask.value = task.description

        binding.viewmodel = viewModel
        builder.setPositiveButton("Edit") { _, _ ->
            viewModel.createNewTask(task.id)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
        }
        var dialog = builder.create()
        dialog.show()
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            dialog.dismiss()
        }
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY)
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(context!!.getColor(R.color.edit))

    }

    fun showBottomSheet() {
        val bottomSheet = CategoryBottomSheetDialogFragment.newInstance()
        bottomSheet.show(
            fragmentManager!!,
            "HOLA"
        )
    }

    fun showdialogError(error : String){
        val builder = MaterialAlertDialogBuilder(context).setTitle("Error").setMessage(error)
            .setPositiveButton("Ok",{_,_->}).show()
    }


}
