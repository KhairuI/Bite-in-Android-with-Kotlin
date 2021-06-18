package com.example.bite_mvvm_sqlite.Utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bite_mvvm_sqlite.interfaces.MyDAO
import com.example.bite_mvvm_sqlite.model.Item

@Database(
    entities = [Item::class],
    version = 1
)
abstract class MyDB:RoomDatabase() {

    abstract val groceryDao:MyDAO

    companion object{
        @Volatile
        private var instance:MyDB?=null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDB(
                        context
                    ).also { instance = it }
            }

        private fun createDB(context: Context)= Room.databaseBuilder(context.applicationContext,
            MyDB::class.java,"shopping_db").build()

    }

}