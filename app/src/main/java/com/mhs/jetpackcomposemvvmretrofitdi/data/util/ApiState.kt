package com.mhs.jetpackcomposemvvmretrofitdi.data.util

import com.mhs.jetpackcomposemvvmretrofitdi.data.model.Post

sealed class ApiState{
    class Success(val data : List<Post>): ApiState()
    class Failure(val msg:Throwable): ApiState()
    object Loading : ApiState()
    object Empty : ApiState()
}