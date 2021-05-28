package com.example.bite_room_multiple_table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department (

    @PrimaryKey(autoGenerate = false)
    val deptName:String

    )


