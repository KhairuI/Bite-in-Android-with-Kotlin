package com.example.bite_mvvm_sqlite.ui_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bite_mvvm_sqlite.repository.GroceryRepo

class MainViewModelFactory(private val repo: GroceryRepo):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}