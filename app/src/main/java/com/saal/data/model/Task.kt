package com.saal.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "title") @NonNull
    var title: String = "",

    @ColumnInfo(name = "description") @NonNull
    var description: String = ""
)