package com.example.notetodoapp.model.dc

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notesTable")
class Note(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "desc")
    val desc: String,
    @ColumnInfo(name = "timestamp")
    val timestamp: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}