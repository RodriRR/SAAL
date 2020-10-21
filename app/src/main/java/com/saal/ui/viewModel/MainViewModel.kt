package com.saal.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.saal.data.model.Category
import com.saal.data.model.Task
import com.saal.data.repository.DatabaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn

/**
 * This is the app main ViewModel that contains all de ui data
 */
class MainViewModel(private val repo: DatabaseRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val coroutineScopeMain = CoroutineScope(viewModelJob + Dispatchers.Main)

    var pruebaTask = repo.pruebaTask().asLiveData()
    var pruebaCategories = repo.pruebaCategories().asLiveData()
    private val searchChanel = ConflatedBroadcastChannel<String>()

    val flow = searchChanel.asFlow().debounce(1000L).flatMapLatest { search ->
        repo.pruebaTaskParameter(search)
    }.flowOn(Dispatchers.IO)

    fun setSearchQuery(search: String) {
        //We use .offer() to send the element to all the subscribers.
        searchChanel.offer(search)
    }

    init {
        searchChanel.offer("")
    }

    var categories = repo.getCategories()
    var tasks = repo.getTasks()

    val titleNewTask = MutableLiveData<String>("")
    val descriptionNewTask = MutableLiveData<String>("")
    val nameNewCategory = MutableLiveData<String>("")

    //Manage createTask
    val categorySelected = MutableLiveData<Category>()

    val taskToShow = MutableLiveData<List<Task>>()
    val filter = MutableLiveData<String>("")

    fun updateTaskToShow(text : String?) {
        if (text.isNullOrEmpty()) {
            coroutineScopeMain.launch {
                try {
                    taskToShow.postValue(tasks.value)
                } catch (e: Exception) {
                    println(e)
                }
            }
        } else {
            coroutineScope.launch {
                try {
                    val tasks = repo.getFilterTask(text)
                    withContext(Dispatchers.Main) {
                        taskToShow.postValue(tasks)
                    }
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
    }

    fun createNewTask(id: Int) {
        coroutineScope.launch {
            val title = titleNewTask.value
            val description = descriptionNewTask.value
            val task = Task(
                id,
                title.toString(),
                description.toString(),
                categorySelected.value!!.id,
                categorySelected.value!!.name
            )
            try {
                repo.insertNewTask(task)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun deleteTask(task: Task) {
        coroutineScope.launch {
            try {
                repo.deleteTask(task)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun deleteCategory(category: Category) {
        coroutineScope.launch {
            try {
                repo.deleteTaskOfCategory(category)
                repo.deleteCategory(category)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun updateCategory(category: Category) {
        coroutineScope.launch {
            try {
                category.name = nameNewCategory.value!!
                repo.updateCategory(category)
                nameNewCategory.postValue("")
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun insertNewCategory(category: Category) {
        coroutineScope.launch {
            try {
                repo.insertNewCategory(category)
                nameNewCategory.postValue("")
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun updateTask(id: Int) {
        coroutineScope.launch {
            val title = titleNewTask.value
            val description = descriptionNewTask.value
            val task = Task(
                id,
                title.toString(),
                description.toString(),
                categorySelected.value!!.id,
                categorySelected.value!!.name
            )
            try {
                repo.updateTask(task)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun clearEditTexts() {
        titleNewTask.value = ""
        descriptionNewTask.value = ""
        nameNewCategory.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}