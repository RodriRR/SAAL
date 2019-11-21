package com.saal.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(  foreignKeys = [ForeignKey(
    entity = Category::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("category_id"),
    onDelete = ForeignKey.CASCADE)]
)
data class Task(
    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "title") @NonNull
    var title: String = "",

    @ColumnInfo(name = "description") @NonNull
    var description: String = "",

    @ColumnInfo(name = "category_id") @NonNull
    var category: Int = 0,

    @ColumnInfo(name = "category_name") @NonNull
    var category_name: String = ""
)