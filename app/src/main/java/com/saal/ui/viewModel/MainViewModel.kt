package com.saal.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saal.data.model.Category
import com.saal.data.model.Task
import com.saal.data.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(repo: DatabaseRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val repo = repo

    var categories = repo.getCategories()
    var tasks = repo.getTasks()

    val titleNewTask = MutableLiveData<String>("")
    val descriptionNewTask = MutableLiveData<String>("")
    val nameNewCategory = MutableLiveData<String>("")

    //Manage createTask
    val categorySelected = MutableLiveData<Category>()

    //Search
    var searchText = MutableLiveData<String>("")
    var searchTask = MutableLiveData<List<Task>>()

    fun createNewTask(id : Int) {
        coroutineScope.launch {
            val title = titleNewTask.value
            val description = descriptionNewTask.value
            val task = Task(id,title.toString(),description.toString(), categorySelected.value!!.id,categorySelected.value!!.name)
            try {
                repo.insertNewTask(task)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun deleteTask(task : Task) {
        coroutineScope.launch {
            try {
                repo.deleteTask(task)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun createNewCategory(id : Int) {
        coroutineScope.launch {
            val name = nameNewCategory.value
            val category = Category(id,name.toString())
            try {
                repo.insertNewCategory(category)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun deleteCategory(category : Category) {
        coroutineScope.launch {
            try {
                repo.deleteCategory(category)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun clearEditTexts(){
        titleNewTask.value = ""
        descriptionNewTask.value = ""
        nameNewCategory.value = ""
    }

    fun prueba() : ArrayList<String>{
        var prueba = arrayListOf<String>()
        if(!categories.value.isNullOrEmpty()) {
            var categories = categories.value
            for (category in categories!!) {
                prueba.add(category.name)
            }
        }
        return prueba
    }

    fun getPrueba(){
        coroutineScope.launch {
            try {
                searchTask.postValue(repo.getSearched(searchText.value!!))
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}