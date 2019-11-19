package com.saal.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true) @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "name") @NonNull
    var name: String = ""
)