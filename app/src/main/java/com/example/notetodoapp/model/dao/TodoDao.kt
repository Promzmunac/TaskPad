package com.example.notetodoapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notetodoapp.model.dc.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("select * from todoTable order by id ASC")
    fun getAllTodo(): LiveData<List<Todo>>


}