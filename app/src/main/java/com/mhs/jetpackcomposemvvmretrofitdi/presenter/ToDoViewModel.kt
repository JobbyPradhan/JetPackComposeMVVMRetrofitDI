package com.mhs.jetpackcomposemvvmretrofitdi.presenter

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.ToDo
import com.mhs.jetpackcomposemvvmretrofitdi.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel
@Inject
constructor(private val todoRepository: TodoRepository):ViewModel() {

    val response : MutableState<List<ToDo>> = mutableStateOf(emptyList())
    fun insert(todo:ToDo) = viewModelScope.launch {
        todoRepository.insertNote(todo)
    }

    init {
        getAllTodos()
    }

  private fun getAllTodos() =viewModelScope.launch {
        todoRepository.getAllTodos()
            .catch { e->
                Log.d("main", "getAllTodos: ${e.message}")
            }.collect{
                response.value = it
            }
    }
}