package com.mhs.jetpackcomposemvvmretrofitdi.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhs.jetpackcomposemvvmretrofitdi.data.dao.ToDoDao
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.ToDo

@Database(entities = [ToDo::class],version = 1,exportSchema = false)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun getDao() : ToDoDao
}