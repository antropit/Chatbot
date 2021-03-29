package ru.antropit.chatbot.feat.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.antropit.chatbot.R
import ru.antropit.chatbot.repo.Message
import java.util.*
import kotlin.collections.ArrayList


class MainAdapter(): RecyclerView.Adapter<MainAdapter.MainViewHolder>(), Filterable {
    companion object {
        var dataList: MutableList<Message> = ArrayList()
        var fullList: List<Message>? = null
    }

    private var recyclerView: RecyclerView? = null
    private val BOT_ITEM = 100

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        val message = fullList!![position]

        return if (message.id == "1") {
            BOT_ITEM
        } else super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        if(viewType == BOT_ITEM)
            return MainViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.chat_item_bot,
                    parent,
                    false
                )
            )

        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.chat_item_self,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class MainViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: Message) {
            view.findViewById<TextView>(R.id.message).text = item.message
        }
    }

    fun updateList(list: List<Message>) {
        val diffUtilCallback = DiffUtilCallback(dataList, list)
        val diffResult =  DiffUtil.calculateDiff(diffUtilCallback)

        dataList.clear()
        dataList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun submitList(list: List<Message>) {
        fullList = ArrayList(list)
        updateList(list)
    }

    inner class DiffUtilCallback(
        private val oldList: List<Message>,
        private val newList: List<Message>
    ): DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItemPosition == newItemPosition
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filteredList: List<Message>
                if(charSequence.isEmpty()) {
                    filteredList = fullList!!.toMutableList()
                } else {
                    val keywords = charSequence.toString().toLowerCase(Locale.getDefault()).trim().split(
                        " "
                    )
                    filteredList = fullList!!.filter(
                        fun(it: Message): Boolean {
                            for (key in keywords)
                                if (!it.message.toLowerCase(Locale.getDefault())
                                        .contains(key)
                                ) return false
                            return true
                        }).toMutableList()
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                this@MainAdapter.updateList(filterResults.values as ArrayList<Message>)
            }
        }
    }

}