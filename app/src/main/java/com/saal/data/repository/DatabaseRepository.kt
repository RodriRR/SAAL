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

    fun insertNewTask(task: Task)
    fun deleteTask(task: Task)

    suspend fun insertNewCategory(category: Category)
    fun deleteCategory(category: Category)

    fun deleteTaskOfCategory(category: Category)

    fun updateCategory(category: Category)

    suspend fun getFilterTask(filter: String): List<Task>

    fun updateTask(task: Task)

    fun pruebaTask() : Flow<List<Task>>
    fun pruebaTaskParameter(parameter : String) : Flow<List<Task>>
    fun pruebaCategories() : Flow<List<Category>>

}

class DatabaseRepositoryImpl(private val todoDatabase : ToDoDatabase) : DatabaseRepository {

    override fun pruebaTask(): Flow<List<Task>> {
        return todoDatabase.todoDatabaseDao.pruebaTask()
    }

    override fun pruebaTaskParameter(parameter : String): Flow<List<Task>> {
        return if(parameter.isNullOrEmpty()){
            pruebaTask()
        }else {
            todoDatabase.todoDatabaseDao.pruebaTaskParameter(parameter)
        }
    }

    override fun pruebaCategories(): Flow<List<Category>> {
        return todoDatabase.todoDatabaseDao.pruebaCategories()
    }

    override fun updateTask(task: Task) {
        todoDatabase.todoDatabaseDao.updateTask(task.id, task.title, task.description, task.category, task.category_name)
    }

    override suspend fun getFilterTask(filter: String): List<Task> {
        return todoDatabase.todoDatabaseDao.getFilterTask(filter)
    }

    override fun insertNewTask(task: Task) {
        todoDatabase.todoDatabaseDao.insertNewTask(task)
    }

    override fun deleteTask(task: Task) {
        todoDatabase.todoDatabaseDao.deleteTask(task)
    }

    override suspend fun insertNewCategory(category: Category) {
        todoDatabase.todoDatabaseDao.insertNewCategory(category)
    }

    override fun deleteCategory(category: Category) {
        todoDatabase.todoDatabaseDao.deleteCategory(category)
    }

    override fun deleteTaskOfCategory(category: Category) {
        todoDatabase.todoDatabaseDao.deleteTaskOfCategory(category.id)
    }

    override fun updateCategory(category: Category) {
        todoDatabase.todoDatabaseDao.updateCategory(category.name, category.id)
        todoDatabase.todoDatabaseDao.updateCategoryTask(category.name, category.id)
    }


}

