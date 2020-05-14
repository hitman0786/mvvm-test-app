package com.mvvm.test.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvvm.test.domain.AppState
import com.mvvm.test.utils.PreferenceUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AuthViewModel : ViewModel() {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private var state = MutableLiveData<AppState<Any>>()

    private var auth: FirebaseAuth = Firebase.auth

    fun userRegistration(email: String, password: String) {
        state.value = AppState.Loading(true)
        scope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("TAG", "createUserWithEmail:success - ${it.result?.user?.email}")
                            userLogin(email, password)
                        } else {
                            val msg = it.exception?.message ?: ""
                            Log.w("TAG", "createUserWithEmail:failure", it.exception)
                            state.value = AppState.Error(msg)
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                val msg = e.message ?: ""
                state.value = AppState.Error(msg)
            }
        }

    }

    fun userLogin(email: String, password: String) {
        state.value = AppState.Loading(true)
        scope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("TAG", "signInWithEmail:success")
                            val user = auth.currentUser
                            val emailID = user?.email ?: ""
                            state.value = AppState.Success(emailID)
                            PreferenceUtils.setPrefEmail(emailID)
                        } else {
                            val msg = it.exception?.message ?: ""
                            Log.w("TAG", "createUserWithEmail:failure", it.exception)
                            state.value = AppState.Error(msg)
                            PreferenceUtils.setPrefEmail("")
                        }
                    }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                val msg = e.message ?: ""
                state.value = AppState.Error(msg)
                PreferenceUtils.setPrefEmail("")
            }
        }
    }

    fun getUIState() = state

    fun cancelRequests() = coroutineContext.cancel()
}