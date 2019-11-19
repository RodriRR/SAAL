package com.saal.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.saal.data.database.ToDoDatabase
import com.saal.data.database.ToDoDatabaseDao
import com.saal.data.model.Category


interface DatabaseRepository {

    fun getCategories(): LiveData<List<Category>>
}

class DatabaseRepositoryImpl(application: Application) : DatabaseRepository {

    private var todoDatabase: ToDoDatabaseDao
    private var categories : LiveData<List<Category>>

    init {
        val database: ToDoDatabase = ToDoDatabase.getInstance(application.applicationContext)
        todoDatabase = database.todoDatabaseDao
        categories = todoDatabase.getCategories()

    }

    override fun getCategories(): LiveData<List<Category>> {
        println(categories.value.toString())
        return categories
    }
}
