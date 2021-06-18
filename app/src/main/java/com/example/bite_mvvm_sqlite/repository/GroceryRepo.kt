package com.example.bite_mvvm_sqlite.repository

import com.example.bite_mvvm_sqlite.Utils.MyDB
import com.example.bite_mvvm_sqlite.model.Item

class GroceryRepo(private val db: MyDB) {

    suspend fun insert(item: Item)= db.groceryDao.insert(item)
    suspend fun delete(item: Item)= db.groceryDao.delete(item)

    fun getAllItem()= db.groceryDao.getAllItem()
}