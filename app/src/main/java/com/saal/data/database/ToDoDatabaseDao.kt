package com.saal.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.saal.data.model.Category
import com.saal.data.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Defines methods for using the ToDoDatabase class with Room.
 */
@Dao
interface ToDoDatabaseDao {

    @Query("SELECT * FROM task")
    fun pruebaTask(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE category_name LIKE :text")
    fun pruebaTaskParameter(text : String) : Flow<List<Task>>

    @Query("SELECT * FROM category")
    fun pruebaCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)

    @Query("DELETE FROM task WHERE category_id = :id")
    fun deleteTaskOfCategory(id: Int)

    @Query("UPDATE category SET name = :newName WHERE id = :id")
    fun updateCategory(newName: String, id: Int)

    @Query("UPDATE task SET category_name= :newName WHERE category_id = :id")
    fun updateCategoryTask(newName: String, id: Int)

    @Query("UPDATE task SET id = :id , title = :title , description = :description , category_id = :category_id , category_name= :category_name WHERE id = :id")
    fun updateTask(id: Int, title : String, description : String, category_id : Int, category_name : String)

    @Query("SELECT * FROM task WHERE category_name LIKE :text")
    suspend fun getFilterTask(text : String) : List<Task>

}