package com.saal.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saal.databinding.FragmentTaskBinding
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.saal.R
import com.saal.data.model.Category
import com.saal.data.model.Task
import com.saal.databinding.ItemCreateTaskBinding
import com.saal.ui.adapters.*


/**
 * This fragment shows the task when open the app
 */
class TaskFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTaskBinding.inflate(inflater)

        //Task Listener
        val adapter = TasksAdapter(TasksListener { task: Task, view: Int ->
            when (view) {
                1 -> {
                    viewModel.clearEditTexts()
                    showDialogEdit(task)
                }
                2 -> viewModel.deleteTask(task)
            }
        })

        //+ button
        binding.floatingActionButton.setOnClickListener {
            if (viewModel.categories.value.isNullOrEmpty()) {
                showDialogError("You have to add a category first")
            } else {
                viewModel.clearEditTexts()
                showDialogCreate()
            }
        }


        binding.bar.setNavigationOnClickListener {
            showBottomSheet()
        }


        viewModel.categories.observe(this, Observer {
            println(it)
        })

        viewModel.tasks.observe(this, Observer {
            println(it)
        })



        binding.taskList.adapter = adapter
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun showDialogCreate() {
        val binding = ItemCreateTaskBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom)
        builder.setView(binding.root)

        val adapter = CategoryCreateTaskAdapter(CategoryListener { category: Category ->
            viewModel.categorySelected.value = category
            Toast.makeText(context, "You have selected ${category.name}", Toast.LENGTH_LONG).show()
        })
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.viewmodel = viewModel
        builder.setPositiveButton("Create") { _, _ ->
            if (viewModel.categorySelected.value != null) {
                viewModel.createNewTask(0)
            } else {
                showDialogError("You have to pick a category")
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

    private fun showDialogEdit(task: Task) {
        val binding = ItemCreateTaskBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom)
        builder.setView(binding.root)

        val adapter = CategoryCreateTaskAdapter(CategoryListener { category: Category ->
            viewModel.categorySelected.value = category
            Toast.makeText(context, "You have selected ${category.name}", Toast.LENGTH_LONG).show()
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
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            dialog.dismiss()
        }
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY)
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(context!!.getColor(R.color.edit))

    }

    private fun showBottomSheet() {
        val bottomSheet = CategoryBottomSheetDialogFragment.newInstance()
        bottomSheet.show(
            fragmentManager!!,
            "sheet"
        )
    }

    private fun showDialogError(error: String) {
        MaterialAlertDialogBuilder(context).setTitle("Error").setMessage(error)
            .setPositiveButton("Ok") { _, _ -> }.show()
    }


}
