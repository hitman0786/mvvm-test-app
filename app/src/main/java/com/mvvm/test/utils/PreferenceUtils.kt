package com.mvvm.test.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences("com.mvvm.test", Context.MODE_PRIVATE)
    }

    fun setPrefEmail(email: String){
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("email",email)
        editor.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("email", "")
    }
}