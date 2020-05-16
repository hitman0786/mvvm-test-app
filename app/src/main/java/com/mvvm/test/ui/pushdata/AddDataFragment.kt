package com.mvvm.test.ui.pushdata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mvvm.test.R
import com.mvvm.test.domain.AppState
import com.mvvm.test.ui.details.viewmodel.AddDataModel
import com.mvvm.test.ui.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_add_data.*

class AddDataFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_data, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //It will create viewmodel new instance each time
        val viewModel: AddDataModel by viewModels()

        buttonSubmit.setOnClickListener {
            viewModel.addDataToServer(editTextTopic.text.toString(), editTextDesc.text.toString())
        }

        viewModel.getUIState().observe(requireActivity(), Observer {
            handleAddDataState (it)
        })
    }

    private fun handleAddDataState(it: AppState<Any>) {
        when(it){
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                Snackbar.make(topLayout, it.data.toString(), Snackbar.LENGTH_LONG).show()
                updateHomeList()
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                Snackbar.make(topLayout, it.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun updateHomeList(){
        val homeViewModel: HomeViewModel by activityViewModels()
        homeViewModel.getTopicsResponse(requireActivity())
        Log.i("TAG", "update home list ")
        //This is use to popup fragment from back stack explicitly
        findNavController().popBackStack(R.id.addDataFragment, true)
    }
}