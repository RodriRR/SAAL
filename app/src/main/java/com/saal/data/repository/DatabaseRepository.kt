package com.saal.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.saal.data.database.ToDoDatabase
import com.saal.data.database.ToDoDatabaseDao
import com.saal.data.model.Category
import com.saal.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This interface and class is responsible for storing the objects returned by the ToDoDatabse API
 * so that the corresponding viewmodels can access them. It contains the necessary methods to
 * access the SQLite data.
 */
interface DatabaseRepository {

    fun getCategories(): LiveData<List<Category>>
    fun getTasks(): LiveData<List<Task>>

    suspend fun insertNewTask(task: Task)
    suspend fun deleteTask(task: Task)

    suspend fun insertNewCategory(category: Category)
    suspend fun deleteCategory(category: Category)

    suspend fun deleteTaskOfCategory(category: Category)

    suspend fun updateCategory(category: Category)

    fun getAllTask(): List<Task>
    suspend fun getFilterTask(filter: String): List<Task>

    suspend fun updateTask(task: Task)

    fun pruebaTask() : Flow<List<Task>>
    fun pruebaTaskParameter(parameter : String) : Flow<List<Task>>
    fun pruebaCategories() : Flow<List<Category>>

}

class DatabaseRepositoryImpl(application: Application) : DatabaseRepository {

    private var todoDatabase: ToDoDatabaseDao
    private var categories: LiveData<List<Category>>
    private var allTask: LiveData<List<Task>>

    init {
        val database: ToDoDatabase = ToDoDatabase.getInstance(application.applicationContext)
        todoDatabase = database.todoDatabaseDao
        categories = todoDatabase.getCategories()
        allTask = todoDatabase.getTasks()
    }

    override fun pruebaTask(): Flow<List<Task>> {
        return todoDatabase.pruebaTask()
    }

    override fun pruebaTaskParameter(parameter : String): Flow<List<Task>> {
        return if(parameter.isNullOrEmpty()){
            pruebaTask()
        }else {
            todoDatabase.pruebaTaskParameter(parameter)
        }
    }

    override fun pruebaCategories(): Flow<List<Category>> {
        return todoDatabase.pruebaCategories()
    }

    override suspend fun updateTask(task: Task) {
        todoDatabase.updateTask(task.id, task.title, task.description, task.category, task.category_name)
    }

    override fun getAllTask(): List<Task> {
        return todoDatabase.getAllTasks()
    }

    override suspend fun getFilterTask(filter: String): List<Task> {
        return todoDatabase.getFilterTask(filter)
    }

    override fun getCategories(): LiveData<List<Category>> {
        return categories
    }

    override fun getTasks(): LiveData<List<Task>> {
        return allTask
    }

    override suspend fun insertNewTask(task: Task) {
        todoDatabase.insertNewTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        todoDatabase.deleteTask(task)
    }

    override suspend fun insertNewCategory(category: Category) {
        todoDatabase.insertNewCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        todoDatabase.deleteCategory(category)
    }

    override suspend fun deleteTaskOfCategory(category: Category) {
        todoDatabase.deleteTaskOfCategory(category.id)
    }

    override suspend fun updateCategory(category: Category) {
        todoDatabase.updateCategory(category.name, category.id)
        todoDatabase.updateCategoryTask(category.name, category.id)
    }


}

