package com.example.bestandroidcode.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bestandroidcode.model.Cat

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFavouriteCat(cat: Cat) : Long

    @Query("SELECT * FROM tb_cat")
    fun getAllFavouriteCats() : LiveData<List<Cat>>

    @Delete
    suspend fun removeFavouriteCat(cat: Cat): Int
}