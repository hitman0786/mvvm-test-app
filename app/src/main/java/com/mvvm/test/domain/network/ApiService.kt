package com.mvvm.test.domain.network

import com.mvvm.test.model.TopicResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("projects/mvvm-test-app/databases/(default)/documents/topics/")
    suspend fun getTopics(): Response<TopicResponse>
}