package com.saal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saal.data.model.Category
import com.saal.data.model.Task


/**
 * A database that stores Tas and Categories information.
 * And a global method to get access to the database.
 */
@Database(entities = [Category::class, Task::class], version = 2, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract val todoDatabaseDao: ToDoDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDatabase::class.java,
                        "todo.db"
                    )
                        .createFromAsset("databases/todo.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}