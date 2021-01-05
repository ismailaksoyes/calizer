package com.avalon.avalon.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.repository.RoomRepository
import com.avalon.avalon.data.repository.Repository
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val dbRepository: RoomRepository, private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dbRepository,repository) as T
        }
        throw IllegalArgumentException("Unknown Model Class")
    }
}