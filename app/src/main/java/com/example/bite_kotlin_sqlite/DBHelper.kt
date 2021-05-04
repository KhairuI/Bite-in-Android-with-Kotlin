package com.example.bite_kotlin_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(private val context: Context):SQLiteOpenHelper(context,DB_NAME,null, VERSION) {

    companion object{
        const val DB_NAME= "myDatabase"
        const val TABLE_NAME="userTable"
        const val NAME="name"
        const val EMAIL="email"
        const val ID="id"
        const val VERSION=1
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val create_table= "CREATE TABLE "+ TABLE_NAME +" " +
                "( "+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME+" VARCHAR(100), "+ EMAIL+" VARCHAR(100) ) ;"
        try {
            db?.execSQL(create_table)
            Toast.makeText(context,"Create Table",Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            Toast.makeText(context,""+e,Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        val upgrade= "DROP TABLE IF EXISTS $TABLE_NAME"
        try {
            db?.execSQL(upgrade)
            Toast.makeText(context,"Upgrade Table",Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            Toast.makeText(context,""+e,Toast.LENGTH_LONG).show()
        }
    }

    // insert data in database....
    fun insert(name:String,email:String):Long{
        val db= this.writableDatabase
        val contentValues= ContentValues()
        contentValues.put(NAME,name)
        contentValues.put(EMAIL,email)
        return db.insert(TABLE_NAME,null,contentValues)

    }
    // retrieve data....
    fun show():Cursor{
        val db= this.readableDatabase
        val getData= "SELECT * FROM $TABLE_NAME"
        return db.rawQuery(getData,null)
    }

    // delete data...
    fun delete(id:String):Int{
        val db= this.writableDatabase
       return db.delete(TABLE_NAME,"$ID = ?", arrayOf(id))
    }

    // for update....

    fun update(user:User):Int{
        val db= this.writableDatabase
        val contentValues= ContentValues()

        contentValues.put(NAME, user.name)
        contentValues.put(EMAIL, user.email)
        return db.update(TABLE_NAME,contentValues,"$ID = ?", arrayOf(user.id))
    }

}