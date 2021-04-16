package ar.team.stockify.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.model.SearchItem
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_FAVOURITE_ITEM = 1
private val ITEM_VIEW_TYPE_SEARCH_ITEM = 2
class AuxAdapter : ListAdapter<Element, RecyclerView.ViewHolder>(SearchItemDiffCallback()), SearchImpl {
/**
    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map   DataItem.SleepNightItem(it)
            }
        }
        withContext(Dispatchers.Main) {
            submitList(items)
        }
    }*/

    class ViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view.rootView) {
        val stock: TextView = view.findViewById(R.id.stock)
        val precio: TextView = view.findViewById(R.id.precio)
        val cambio: TextView = view.findViewById(R.id.cambio)
        fun bind(item: SearchItem) {
            stock.text = item.symbol
            precio.text = item.currency
            cambio.text = item.matchScore
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_element, parent, false)
                return ViewHolder(view)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Element.Header -> ITEM_VIEW_TYPE_HEADER
            is Element.ElmenentItem -> ITEM_VIEW_TYPE_SEARCH_ITEM
            is Element.FavoriteItem -> ITEM_VIEW_TYPE_FAVOURITE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuxAdapter.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_FAVOURITE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW_TYPE_SEARCH_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }






    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(filter: String) {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(filter: String) {
        TODO("Not yet implemented")
    }

}
class SearchItemDiffCallback : DiffUtil.ItemCallback<Element>() {
    override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem == newItem
    }
}
sealed class Element {
    data class ElmenentItem(val searchItem: SearchItem) : Element() {
        override val id = searchItem.symbol
    }

    data class FavoriteItem(val searchItem: SearchItem) : Element() {
        override val id = searchItem.symbol
    }

    object Header : Element() {
        override val id = String()
    }

    abstract val id: String
}
