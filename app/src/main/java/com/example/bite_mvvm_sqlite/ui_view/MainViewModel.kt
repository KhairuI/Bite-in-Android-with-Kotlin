package com.example.bite_mvvm_sqlite.ui_view

import androidx.lifecycle.ViewModel
import com.example.bite_mvvm_sqlite.model.Item
import com.example.bite_mvvm_sqlite.repository.GroceryRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository:GroceryRepo):ViewModel() {

    fun insert(item: Item)= CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }

    fun delete(item: Item)= CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllItem() = repository.getAllItem()
}