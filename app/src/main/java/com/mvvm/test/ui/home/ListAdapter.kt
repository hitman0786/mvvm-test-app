package com.mvvm.test.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.test.R
import com.mvvm.test.model.Description
import com.mvvm.test.model.Topic
import com.mvvm.test.model.TopicData
import kotlinx.android.synthetic.main.item_row_home.view.*

class ListAdapter(
    private val list: List<TopicData>, val homeFragment: HomeFragment
):  RecyclerView.Adapter<ListAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TopicViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val data = list[position]
        data.fields?.topic?.let { data.fields.description?.let { it1 ->
            holder.bind(it,
                it1, homeFragment)
        } }
    }

    override fun getItemCount(): Int = list.size


    class TopicViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_row_home, parent, false)) {
        private var textViewTopic = itemView.textViewTopic
        private var textViewDesp = itemView.textViewDesp
        private var rowCard = itemView.rowCard

        fun bind(topic: Topic, description: Description, homeFragment: HomeFragment) {
            textViewTopic?.text = topic.stringValue
            textViewDesp?.text = description.stringValue

            rowCard.setOnClickListener {
                homeFragment.navigateToDetailPage(topic, description)
            }
        }

    }
}