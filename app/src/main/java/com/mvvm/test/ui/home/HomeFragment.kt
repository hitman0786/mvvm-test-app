package com.mvvm.test.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.test.R
import com.mvvm.test.domain.AppState
import com.mvvm.test.model.Description
import com.mvvm.test.model.Topic
import com.mvvm.test.model.TopicData
import com.mvvm.test.ui.home.viewmodel.HomeViewModel
import com.mvvm.test.utils.NetworkConnection
import com.mvvm.test.utils.currentNavigationFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

    /**
     * activityViewModels() is use if you want shared viewModel
     * means use same instance of viewModel throughout the app
     * and activityViewModels() also use the activity/fragment where
     * we want same instance
     */
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(NetworkConnection.isNetworkConnected(requireActivity())) {
            Log.i("TAG", "update home screen UI when internet connected")
            viewModel.getTopicsResponse(requireActivity())
        }else{
            viewModel.getTopicsDataFromLocalDatabase(requireActivity())
            Log.i("TAG", "update home screen UI when internet is not connected")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentFragment = requireActivity().supportFragmentManager.currentNavigationFragment
        viewModel.getUIState().observe(requireActivity(), Observer {
            if (currentFragment != null && currentFragment.isAdded && currentFragment.isVisible) {
                handleHomeState(it)
            }
        })

        floatingActionButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_homeFragment_to_addDataFragment)
        }
    }


    private fun handleHomeState(it: AppState<Any>) {
        when(it){
            is AppState.Loading -> {
                Log.i("TAG", "update home screen UI loading state ")
                progressLayout.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
                floatingActionButton.visibility = View.GONE
            }
            is AppState.Success -> {
                progressLayout.visibility = View.GONE
                errorLayout.visibility = View.GONE
                floatingActionButton.visibility = View.VISIBLE

                dataListRecyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)

                    val listData = it.data as? List<*>
                    Log.i("TAG", "update home screen UI success state - ${listData?.size}")
                    if (listData != null) {
                        val data = listData.filterIsInstance<TopicData>()
                        val sortedList = data.sortedWith(compareBy({ it.fields?.time?.stringValue }, { it.fields?.topic?.stringValue }))
                        adapter = ListAdapter(sortedList, this@HomeFragment)
                    }
                }

            }
            is AppState.Error -> {
                Log.i("TAG", "update home screen UI error state")
                progressLayout.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
                textViewErrorMessage.text = it.msg
                floatingActionButton.visibility = View.VISIBLE
            }
        }
    }

    fun navigateToDetailPage(topic: Topic, description: Description){
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
            topic = topic.stringValue,description = description.stringValue)
        val navController = findNavController()
        navController.navigate(action)
    }

    override fun onDestroy() {
        Log.i("TAG", "onDestroy")
        viewModel.cancelRequests()
        super.onDestroy()
    }
}