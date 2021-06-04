package com.example.bite_room_multiple_table

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DeptWithStudent(
    @Embedded val dept:Department,

    @Relation(
        parentColumn = "deptName",
        entityColumn = "id",
        associateBy = Junction(StudentDeptRelation::class)

    )
    val students: List<Student>
)