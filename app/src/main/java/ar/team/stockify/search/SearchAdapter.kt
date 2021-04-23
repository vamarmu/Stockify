package ar.team.stockify.search

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.model.SearchItem
import java.util.stream.Collectors
import java.util.stream.Stream

private val TYPE_HEADER_ITEM = 0
private val TYPE_FAVOURITE_ITEM = 1

class SearchAdapter : ListAdapter<Symbol, RecyclerView.ViewHolder>(SearchItemDiffCallback()),
    SearchImpl {

    private var filter_actual = ""
    private lateinit var list_aux: MutableList<Symbol>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        list_aux = currentList
        return when (viewType) {
            TYPE_HEADER_ITEM -> SearchAdapter.HeaderViewHolder.from(parent)
            TYPE_FAVOURITE_ITEM -> SearchAdapter.ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as Symbol.FavoriteSymbol
                holder.bind(item.searchItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Symbol.Header -> TYPE_HEADER_ITEM
            is Symbol.FavoriteSymbol -> TYPE_FAVOURITE_ITEM
        }
    }


    override fun onQueryTextSubmit(filter: String) {
        textFilter(filter)
    }

    override fun onQueryTextChange(filter: String) {
        if (filter.length == 1 && filter_actual != filter) {
            filter_actual = filter
            //  Llamada al api
        } else {
            textFilter(filter)
        }

    }

    private fun textFilter(filter: String) {
        if (filter.isEmpty()) {
            currentList.clear()
            currentList.addAll(list_aux)
        } else {
            currentList.clear()
            val collect =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    currentList.stream().filter {
                        it.symbol.contains(filter)
                    }
                        .collect(Collectors.toList())
                } else {
                    TODO("VERSION.SDK_INT < N")
                }
            collect?.let {
                currentList.addAll(it)
            }
        }
    }

    class HeaderViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header_search_element, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }

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
            fun from(parent: ViewGroup): SearchAdapter.ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_element, parent, false)
                return ViewHolder(view)
            }
        }
    }
}


class SearchItemDiffCallback : DiffUtil.ItemCallback<Symbol>() {
    override fun areItemsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(oldItem: Symbol, newItem: Symbol): Boolean {
        return oldItem == newItem
    }
}

class SearchClickListener() {

}

sealed class Symbol {


    data class FavoriteSymbol(val searchItem: SearchItem) : Symbol() {
        override val symbol = searchItem.symbol
    }

    object Header : Symbol() {
        override val symbol = String()
    }

    abstract val symbol: String
}