package com.example.bite_room_multiple_table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDept(department: Department):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentDept(studentDeptRelation: StudentDeptRelation):Long
}