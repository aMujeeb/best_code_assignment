package com.example.bestandroidcode.network.repository

import com.example.bestandroidcode.model.Cat

interface CatDataRepository {
    suspend fun getRandomCat() : List<Cat>
    suspend fun getCatBasedOnSelectedCategory(category_ids: String) : List<Cat>
}