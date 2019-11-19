package com.saal.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.saal.data.model.Category

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface ToDoDatabaseDao {

    @Query("SELECT * FROM category")
    fun getCategories() : LiveData<List<Category>>
}