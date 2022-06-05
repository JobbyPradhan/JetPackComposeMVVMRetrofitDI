package com.mhs.jetpackcomposemvvmretrofitdi.presenter

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.ToDo
import com.mhs.jetpackcomposemvvmretrofitdi.ui.theme.JetPackComposeMVVMRetrofitDITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity : ComponentActivity() {

    private val todoViewModel : ToDoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetPackComposeMVVMRetrofitDITheme {
                Surface(color = MaterialTheme.colors.background) {
                    AddToolbar()
                }
            }
        }
    }

    @Composable
    fun AddToolbar(){
        Scaffold(
            topBar = {
                TopAppBar (
                    title = {
                        Text(text = "ToDo App")
                    }
                )
            },
            floatingActionButton = {
                val openDialog = remember {
                    mutableStateOf(false)
                }
                FloatingActionButton(onClick = {
                   openDialog.value = true

                }) {
                    AddDialogBox(openDialog = openDialog)
                    Icon(Icons.Default.Add, contentDescription ="Add" )
                }
            }
        ) {
            RecyclerView(todoViewModel = todoViewModel)
        }
    }

    @Composable
    fun AddDialogBox(openDialog:MutableState<Boolean>){
        val title = remember {
            mutableStateOf("")
        }
        val description = remember {
            mutableStateOf("")
        }
        if(openDialog.value){
            AlertDialog(onDismissRequest = {
                openDialog.value = false
            },
                title = {
                    Text(text = "Add Todo")
                },
                text = {
                    Column(modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()) {
                        OutlinedTextField(
                            value = title.value ,
                            onValueChange = {
                                title.value = it
                            },
                            placeholder = {
                                Text(text = "Enter title")
                            },
                            label = {
                                Text(text = "Title")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                        OutlinedTextField(
                            value = description.value ,
                            onValueChange = {
                                description.value = it
                            },
                            placeholder = {
                                Text(text = "Enter description")
                            },
                            label = {
                                Text(text = "Description")
                            },
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                },
                confirmButton = {
                    OutlinedButton(onClick = {
                        insert(title, description)
                        openDialog.value =false
                        title.value = ""
                        description.value = ""
                    }) {
                        Text(text = "Save")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = {
                        openDialog.value =false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }

    private fun insert(title:MutableState<String>,description:MutableState<String>){
        lifecycleScope.launchWhenStarted {
            if(!TextUtils.isEmpty(title.value) && !TextUtils.isEmpty(description.value)){
                val toDo = ToDo(title.value,description.value)
                todoViewModel.insert(toDo)
                Toast.makeText(this@TodoActivity, "Insert Data", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@TodoActivity, "Empty Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EachRow(toDo: ToDo){
        Card(modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(6.dp),
            onClick = {
                val intent = Intent(this,RetrofitActivity::class.java)
                startActivity(intent)
            }
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = toDo.title ,
                fontWeight = FontWeight.Bold)
                Text(text = toDo.description,modifier = Modifier.padding(vertical = 8.dp),
                fontStyle = FontStyle.Italic)
            }
        }
    }

    @Composable
    fun RecyclerView(todoViewModel:ToDoViewModel){
        LazyColumn{
            items(todoViewModel.response.value){todo->
                EachRow(toDo = todo)
            }
        }
    }
}