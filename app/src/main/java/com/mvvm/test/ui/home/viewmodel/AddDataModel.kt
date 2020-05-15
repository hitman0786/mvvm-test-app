package com.mvvm.test.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mvvm.test.domain.AppState

class AddDataModel: ViewModel() {

    private var state = MutableLiveData<AppState<Any>>()

    var db = FirebaseFirestore.getInstance()

    fun addDataToServer(topic: String, desp: String) {
        state.value = AppState.Loading(true)//data start loading
        val data = hashMapOf(
            "topic" to topic,
            "description" to desp
        )

        db.collection("topics").document()
            .set(data)
            .addOnSuccessListener {
                state.value = AppState.Success("Data Added Successfully")
            }
            .addOnFailureListener {
                state.value = AppState.Error("Something went wrong, please try again!")
            }
    }

    fun getUIState()  = state
}