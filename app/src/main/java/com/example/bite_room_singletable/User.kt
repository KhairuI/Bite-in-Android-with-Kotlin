package com.example.bite_room_singletable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
   // @ColumnInfo(name="")
    val name:String,
    val email:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?= null
}