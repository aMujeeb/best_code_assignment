package com.example.bestandroidcode.data

import com.example.bestandroidcode.common.Resource
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.network.CatDataRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val dataRepo : CatDataRepositoryImpl
) {

    suspend fun requestRandomCat() : Flow<Resource<List<Cat>>> = flow {
        try {
            emit(Resource.Loading<List<Cat>>())
            val randomCat = dataRepo.getRandomCat()
            emit(Resource.Success<List<Cat>>(randomCat))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Cat>>("Un Expected Error. Please Try again."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Cat>>("Network Connectivity Exception"))
        }
    }.flowOn(Dispatchers.IO)
}