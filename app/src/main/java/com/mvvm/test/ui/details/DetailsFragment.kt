package com.mvvm.test.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mvvm.test.R
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val topic = arguments?.let { DetailsFragmentArgs.fromBundle(it).topic }
        val desp = arguments?.let { DetailsFragmentArgs.fromBundle(it).description }

        textViewTitle.text = topic
        textViewDesc.text = desp


        floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_addDataFragment)
        }
    }
}