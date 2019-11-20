package com.saal.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saal.data.model.Category
import com.saal.data.model.Task

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface ToDoDatabaseDao {

    @Query("SELECT * FROM category")
    fun getCategories() : LiveData<List<Category>>

    @Query("SELECT * FROM task")
    fun getTasks() : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTask(task : Task)

    @Delete
    suspend  fun deleteTask(task : Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCategory(category : Category)

    @Delete
    suspend  fun deleteCategory(category: Category)

}