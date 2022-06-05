package com.mhs.jetpackcomposemvvmretrofitdi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDo: ToDo)

    @Query("SELECT * From toDoTable")
    fun getAllTodos(): Flow<List<ToDo>>
}