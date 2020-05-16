package com.mvvm.test.ui.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.test.R
import com.mvvm.test.domain.AppState
import com.mvvm.test.domain.repository.TopicRepository
import com.mvvm.test.localdb.AppDatabase
import com.mvvm.test.model.TopicData
import com.mvvm.test.model.TopicResponse
import kotlinx.coroutines.*
import retrofit2.HttpException

class HomeViewModel: ViewModel() {

    private var state = MutableLiveData<AppState<Any>>()

    fun getTopicsResponse(context: Context){
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
                        saveDataInToLocalDatabase(context, response.body()!!)
                    } else {
                        Log.i("TAG", response.errorBody().toString())
                        state.value = AppState.Error(response.errorBody().toString())
                    }
                }
            } catch (e: HttpException) {
                Log.i("TAG", "Home View Model getting http exception")
                state.value = AppState.Error(context.resources.getString(R.string.exception))
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("TAG", "Home View Model getting exception")
                state.value = AppState.Error(context.resources.getString(R.string.exception))
            }
        }
    }

    private fun saveDataInToLocalDatabase(context: Context, data: TopicResponse){
        val db = AppDatabase.getInstance(context)
        CoroutineScope(Dispatchers.IO).launch {
            db.topicDao().delete()
            db.topicDao().insertAll(data)
        }
    }

    //Getting data from local database
    fun getTopicsDataFromLocalDatabase(context: Context){
        val db = AppDatabase.getInstance(context)
        Log.i("TAG", "Home View Model local database access")
        state.value = AppState.Loading(true)//data start loading
        CoroutineScope(Dispatchers.Main).launch {
            val response = CoroutineScope(Dispatchers.Default).async {
                db.topicDao().getAll()
            }
            if(response.await().isNotEmpty()){
                state.value = AppState.Success(response.await()[0].documents as List<TopicData>)
            }else{
                state.value = AppState.Error(context.resources.getString(R.string.exception))
            }
        }
    }

    fun getUIState() = state

    fun cancelRequests() = TopicRepository.cancelRequests()
}