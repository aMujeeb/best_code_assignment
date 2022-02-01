package com.example.bestandroidcode.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_cat")
data class Cat (
    @PrimaryKey
    val id: String,
    val url: String,
    val height: Int,
    val width: Int
)