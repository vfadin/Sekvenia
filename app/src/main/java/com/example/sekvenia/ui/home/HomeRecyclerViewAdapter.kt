package com.example.sekvenia.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sekvenia.databinding.ItemHomeRecyclerViewBinding

class HomeRecyclerViewAdapter : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {


    //    TODO("Datalist")
    private var dataList: MutableList<String> = mutableListOf("1", "12", "123")
    lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ItemHomeRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.textViewTitle.text = data
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemHomeRecyclerViewBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

}