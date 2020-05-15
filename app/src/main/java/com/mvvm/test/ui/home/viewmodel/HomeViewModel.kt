package com.mvvm.test.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.test.domain.AppState
import com.mvvm.test.domain.repository.TopicRepository
import com.mvvm.test.model.TopicData
import kotlinx.coroutines.*
import retrofit2.HttpException

class HomeViewModel: ViewModel() {

    private var state = MutableLiveData<AppState<Any>>()

    fun getTopicsResponse(){
        Log.i("TAG", "Home View Model")
        state.value = AppState.Loading(true)//data start loading

        CoroutineScope(Dispatchers.Main).launch {
            val response = TopicRepository.getALlTopics().await()
            try {
                Log.i("TAG", "getiing response from firebase")
                if(response != null) {
                    if (response.isSuccessful) {
                        Log.i("TAG", response.body().toString())
                        state.value = AppState.Success(response.body()?.documents as List<TopicData>)
                    } else {
                        Log.i("TAG", response.errorBody().toString())
                        state.value = AppState.Error(response.errorBody().toString())
                    }
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun getUIState() = state

    fun cancelRequests() = TopicRepository.cancelRequests()
}