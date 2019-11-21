package com.saal.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saal.data.database.ToDoDatabase
import com.saal.data.database.ToDoDatabaseDao
import com.saal.data.model.Category
import com.saal.data.model.Task


interface DatabaseRepository {

    fun getCategories(): LiveData<List<Category>>
    fun getTasks(): LiveData<List<Task>>

    suspend fun insertNewTask(task : Task)
    suspend fun deleteTask(task : Task)

    suspend fun insertNewCategory(category : Category)
    suspend fun deleteCategory(category: Category)

    suspend fun deleteTaskOfCategory(category : Category)

    suspend fun updateCategory(category: Category)

}

class DatabaseRepositoryImpl(application: Application) : DatabaseRepository {

    private var todoDatabase: ToDoDatabaseDao
    private var categories : LiveData<List<Category>>
    private var tasks : LiveData<List<Task>>

    init {
        val database: ToDoDatabase = ToDoDatabase.getInstance(application.applicationContext)
        todoDatabase = database.todoDatabaseDao
        categories = todoDatabase.getCategories()
        tasks = todoDatabase.getTasks()
    }

    override fun getCategories(): LiveData<List<Category>> {
        println(categories.value.toString())
        return categories
    }

    override fun getTasks(): LiveData<List<Task>> {
        println(tasks.value.toString())
        return tasks
    }

    override suspend fun insertNewTask(task : Task) {
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
        todoDatabase.updateCategory(category.name,category.id)
        todoDatabase.updateCategoryTask(category.name,category.id)
    }
}

