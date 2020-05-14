package com.mvvm.test.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mvvm.test.R
import com.mvvm.test.domain.AppState
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment: Fragment() {

    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.getUIState().observe(requireActivity(), Observer {
            handleLoginState (it)
        })

        buttonRegister.setOnClickListener {
            viewModel.userRegistration(editTextEmail.text.toString(), editTextPassword.text.toString())
        }

    }

    private fun showError(msg: String){
        progressLayout.visibility = View.GONE
        Snackbar.make(topLayout, msg, Snackbar.LENGTH_LONG).show()
    }

    private  fun  handleLoginState (it : AppState<Any>) {
        when(it){
            is AppState.Loading -> {
                progressLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                progressLayout.visibility = View.GONE
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                val navController = findNavController()
                navController.navigate(R.id.action_registerFragment_to_homeFragment,null, navOptions)
            }
            is AppState.Error -> {
                showError(it.msg)
            }
        }
    }

    override fun onStop() {
        viewModel.cancelRequests()// cancel request at any time
        super.onStop()
    }


}