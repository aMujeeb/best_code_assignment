package com.example.bestandroidcode.network

import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.api.CatAPI
import com.example.bestandroidcode.network.repository.CatDataRepository
import javax.inject.Inject

class CatDataRepositoryImpl @Inject constructor(
    private val api : CatAPI
) : CatDataRepository {
    override suspend fun getRandomCat(): List<Cat> {
        return api.getCatRandom()
    }

    override suspend fun getCatBasedOnSelectedCategory(category_ids: String): List<Cat> {
        return api.getCatBasedOnCategory(category_ids)
    }
}