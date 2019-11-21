package com.saal.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saal.data.model.Category
import com.saal.data.model.Task

/**
 * Defines methods for using the ToDoDatabase class with Room.
 */
@Dao
interface ToDoDatabaseDao {

    @Query("SELECT * FROM category")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM task")
    fun getTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)


    @Query("DELETE FROM task WHERE category_id = :id")
    fun deleteTaskOfCategory(id: Int)

    @Query("UPDATE category SET name = :newName WHERE id = :id")
    fun updateCategory(newName: String, id: Int)

    @Query("UPDATE task SET category_name= :newName WHERE category_id = :id")
    fun updateCategoryTask(newName: String, id: Int)

}