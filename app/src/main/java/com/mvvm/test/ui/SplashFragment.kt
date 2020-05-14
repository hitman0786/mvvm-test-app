package com.mvvm.test.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvvm.test.R
import com.mvvm.test.utils.PreferenceUtils

class SplashFragment : Fragment() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    private val mRunnable: Runnable = Runnable {
        val navController = findNavController()
        if(PreferenceUtils.getEmail() != null && PreferenceUtils.getEmail()?.isNotEmpty()!!) {
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }else{
            navController.navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Initialize the Handler
        mDelayHandler = Handler()
        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}