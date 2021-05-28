package com.example.bite_room_multiple_table

import androidx.room.Entity

@Entity(primaryKeys = ["id","deptName"])
data class StudentDeptRelation (

    val id:String,
    val deptName:String
        )