package com.example.mygrocery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class GroceryViewModelFactory(private val repository: GroceryRepository): ViewModelProvider.NewInstanceFactory() {

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return GroceryViewModel(repository) as T
//    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}