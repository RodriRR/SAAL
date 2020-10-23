package com.saal.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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

    var allCategories = repo.pruebaCategories().asLiveData()
    private val searchChanel = ConflatedBroadcastChannel<String>()

    val allTasks = searchChanel.asFlow().debounce(1000L).flatMapLatest { search ->
        repo.pruebaTaskParameter(search)
    }.flowOn(Dispatchers.IO)

    fun setSearchQuery(search: String) {
        //We use .offer() to send the element to all the subscribers.
        searchChanel.offer(search)
    }

    init {
        searchChanel.offer("")
    }

    val titleNewTask = MutableLiveData("")
    val descriptionNewTask = MutableLiveData("")
    val nameNewCategory = MutableLiveData("")

    //Manage createTask
    val categorySelected = MutableLiveData<Category>()

    val filter = MutableLiveData<String>("")

    fun createNewTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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
                Log.e("ERROR: " , e.toString())
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                repo.deleteTask(task)
            } catch (e: Exception) {
                Log.e("ERROR: " , e.toString())
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteTaskOfCategory(category)
                repo.deleteCategory(category)
            } catch (e: Exception) {
                Log.e("ERROR: " , e.toString())
            }
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                category.name = nameNewCategory.value!!
                repo.updateCategory(category)
                nameNewCategory.postValue("")
            } catch (e: Exception) {
                Log.e("ERROR: " , e.toString())
            }
        }
    }

    fun insertNewCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.insertNewCategory(category)
                nameNewCategory.postValue("")
            } catch (e: Exception) {
                Log.e("ERROR: " , e.toString())
            }
        }
    }

    fun updateTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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
                Log.e("ERROR: " , e.toString())
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
        viewModelScope.cancel()
    }
}