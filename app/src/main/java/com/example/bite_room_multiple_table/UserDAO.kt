package com.example.bite_room_multiple_table

import androidx.room.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDept(department: Department):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentDept(studentDeptRelation: StudentDeptRelation):Long

    @Transaction
    @Query("SELECT * FROM department WHERE deptName = :name")
    fun getStudentWithDepartment(name:String): DeptWithStudent

    // get all student
    @Query("SELECT * FROM student")
    fun getAllStudent(): List<Student>
}