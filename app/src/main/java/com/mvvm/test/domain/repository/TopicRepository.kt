package com.mvvm.test.domain.repository

import com.mvvm.test.domain.network.RestClient
import com.mvvm.test.model.TopicResponse
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

object TopicRepository {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private val service = RestClient.getRetrofitInstance()

    suspend fun getALlTopics(): Deferred<Response<TopicResponse>?> {
        return scope.async {
             service?.getTopics()
        }
    }

    fun cancelRequests() = coroutineContext.cancel()
}