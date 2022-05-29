package com.example.sekvenia.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.example.sekvenia.domain.entity.HomeRecyclerViewItem

class HomeDiffUtils(
    private val oldList : List<HomeRecyclerViewItem>,
    private val newList : List<HomeRecyclerViewItem>
) : DiffUtil.Callback()
{
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}