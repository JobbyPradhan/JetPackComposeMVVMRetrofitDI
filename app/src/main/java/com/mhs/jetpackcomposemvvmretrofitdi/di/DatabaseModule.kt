package com.mhs.jetpackcomposemvvmretrofitdi.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhs.jetpackcomposemvvmretrofitdi.data.TodoDatabase
import com.mhs.jetpackcomposemvvmretrofitdi.data.dao.ToDoDao
import com.mhs.jetpackcomposemvvmretrofitdi.data.model.ToDo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application):TodoDatabase =
        Room.databaseBuilder(application,TodoDatabase::class.java,"TodoDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesDao(db:TodoDatabase):ToDoDao = db.getDao()
}