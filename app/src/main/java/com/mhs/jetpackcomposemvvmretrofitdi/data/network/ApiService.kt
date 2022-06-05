package com.mhs.jetpackcomposemvvmretrofitdi.data.network

import com.mhs.jetpackcomposemvvmretrofitdi.data.model.Post
import retrofit2.http.GET

interface ApiService {

    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts")
    suspend fun getPost():List<Post>


}