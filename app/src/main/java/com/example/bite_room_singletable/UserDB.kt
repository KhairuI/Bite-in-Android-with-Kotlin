package com.example.bite_room_singletable

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class],version = 1)
abstract class UserDB:RoomDatabase() {

    abstract val myDao:UserDAO

    companion object{

        @Volatile
        private var instance:UserDB?= null

        fun getInstance(context: Context):UserDB{
            synchronized(this){
                return  instance?: Room.databaseBuilder(context.applicationContext,
                UserDB::class.java,"user_db").allowMainThreadQueries().build()
                    .also {
                        instance= it
                    }

            }
        }

    }
}