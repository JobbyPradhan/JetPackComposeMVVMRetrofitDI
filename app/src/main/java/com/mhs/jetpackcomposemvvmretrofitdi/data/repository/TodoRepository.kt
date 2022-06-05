package com.mhs.jetpackcomposemvvmretrofitdi.data.repository

import com.mhs.jetpackcomposemvvmretrofitdi.data.dao.ToDoDao
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(private val toDoDao: ToDoDao) {

   suspend fun insertNote(toDo:ToDo)= withContext(Dispatchers.IO){
       toDoDao.insert(toDo)
   }

    fun getAllTodos():Flow<List<ToDo>> = toDoDao.getAllTodos()
}