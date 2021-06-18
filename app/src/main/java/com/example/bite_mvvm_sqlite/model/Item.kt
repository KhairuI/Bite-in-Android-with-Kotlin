package com.example.bite_mvvm_sqlite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_table")
data class Item (
    @ColumnInfo(name="item_name")
    val name:String,
    @ColumnInfo(name = "item_amount")
    var amount:Int
    ){
    @PrimaryKey(autoGenerate = true)
    var id:Int?= null
}