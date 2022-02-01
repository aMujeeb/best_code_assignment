package com.example.bestandroidcode.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bestandroidcode.data.db.dao.CatDao
import com.example.bestandroidcode.model.Cat

@Database(
    entities = [Cat::class],
    version = 1
)
abstract class BestAppCatDb : RoomDatabase(){
    abstract fun getCatDao() : CatDao
}