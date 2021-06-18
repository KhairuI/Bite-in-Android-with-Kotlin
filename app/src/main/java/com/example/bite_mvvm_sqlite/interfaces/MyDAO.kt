package com.example.bite_mvvm_sqlite.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bite_mvvm_sqlite.model.Item

@Dao
interface MyDAO {

    // for insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item):Long

    // for delete
    @Delete
    suspend fun delete(item: Item):Int

    // get all item
    @Query("SELECT * FROM grocery_table")
     fun getAllItem():LiveData<MutableList<Item>>

}