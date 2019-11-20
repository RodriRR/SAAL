package com.saal.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saal.databinding.FragmentTaskBinding
import com.saal.ui.adapters.TasksAdapter
import com.saal.ui.adapters.TasksListener
import com.saal.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import android.view.LayoutInflater
import com.saal.R
import com.saal.data.model.Task
import com.saal.databinding.ItemCreateTaskBinding
import kotlinx.android.synthetic.main.item_create_task.view.*


/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class TaskFragment : Fragment() {

    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentTaskBinding.inflate(inflater)

        val adapter = TasksAdapter(TasksListener { task : Task, view : Int ->
            when(view){
                1 -> {
                    viewModel.clearEditTexts()
                    showDialogEdit(task)
                }
                2 -> viewModel.deleteTask(task)
            }
        })



        binding.taskList.adapter = adapter
        binding.viewmodel = viewModel

        binding.floatingActionButton.setOnClickListener {
            viewModel.clearEditTexts()
            showDialogCreate()
        }

        binding.bar.setOnClickListener {
            showBottomSheet()
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    private fun showDialogCreate(){
        val binding =  ItemCreateTaskBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context,R.style.AlertDialogCustom)
        builder.setView(binding.root)
        binding.viewmodel = viewModel
        builder.setPositiveButton("Create") { _, _ ->
            viewModel.createNewTask(0)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
        }
        var dialog = builder.create()
        dialog.show()
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            dialog.dismiss()
        }
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE)
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
    }

    private fun showDialogEdit(task: Task){
        val binding =  ItemCreateTaskBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context,R.style.AlertDialogCustom)
        builder.setView(binding.root)

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
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE)
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.CYAN)

    }

    fun showBottomSheet() {
        val bottomSheet = CategoryBottomSheetDialogFragment.newInstance()
        bottomSheet.show(
            fragmentManager!!,
            "HOLA"
        )
    }


}
