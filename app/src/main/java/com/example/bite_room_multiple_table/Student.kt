package com.example.bite_room_multiple_table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    val name:String,

    @PrimaryKey(autoGenerate = false)
    val id:String,

    val image:String,
    val dept:String


)