package com.mhs.jetpackcomposemvvmretrofitdi.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.Post
import com.mhs.jetpackcomposemvvmretrofitdi.data.util.ApiState
import com.mhs.jetpackcomposemvvmretrofitdi.ui.theme.JetPackComposeMVVMRetrofitDITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RetrofitActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetPackComposeMVVMRetrofitDITheme {
                androidx.compose.material.Surface(color = MaterialTheme.colors.background) {
                    GetData(mainViewModel= mainViewModel)
                }
                
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EachRow(post: Post){
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            shape = RoundedCornerShape(4.dp),
            onClick = {
                Toast.makeText(this@RetrofitActivity, "Hi", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = post.body,modifier = Modifier.padding(10.dp),fontStyle = FontStyle.Italic)
        }
    }

    @Composable
    fun GetData(mainViewModel : MainViewModel){
        when(val result = mainViewModel.response.value){
            ApiState.Empty ->{
                
            }
            is ApiState.Failure -> {
                Text(text = "${result.msg}")
            }
            ApiState.Loading -> {
                Column(
                     verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }

            }
            is ApiState.Success -> {
                LazyColumn{
                    items(result.data){ response->
                        EachRow( response)
                    }
                }

            }
        }
    }
}