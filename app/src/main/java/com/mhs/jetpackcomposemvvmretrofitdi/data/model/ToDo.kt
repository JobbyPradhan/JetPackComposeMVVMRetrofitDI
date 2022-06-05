package com.mhs.jetpackcomposemvvmretrofitdi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toDoTable")
data class ToDo(
    var title:String,
    var description:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int ?= null
}