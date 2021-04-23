package ar.team.stockify.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.model.BestMatches
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors

private const val TYPE_HEADER_ITEM = 0
private const val TYPE_FAVOURITE_ITEM = 1

class SearchAdapter(private val clickListener: SearchClickListener) :
    ListAdapter<Symbol, RecyclerView.ViewHolder>(SearchItemDiffCallback()),
    SearchImpl {

    private lateinit var list_aux: MutableList<Symbol>
    private val adapterScope= CoroutineScope(Dispatchers.Default)

    fun addListWithoutHeader(list: List<BestMatches>?){
        adapterScope.launch {
            val symbols = list?.map { Symbol.FavoriteSymbol(it)
            }
            withContext(Dispatchers.Main) {
                submitList(symbols)
            }
        }
    }

    fun addListWithHeader(list:List<BestMatches>?){
        adapterScope.launch {
            val symbols = when (list) {
                null -> listOf(Symbol.Header)
                else -> listOf(Symbol.Header) + list.map { Symbol.FavoriteSymbol(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(symbols)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        list_aux = currentList
        Log.d("ADAPTER", viewType.toString())
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
                holder.bind(clickListener, item.bestMatches)
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

        textFilter(filter)

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
        private val stock: TextView = view.findViewById(R.id.stock)
        private val precio: TextView = view.findViewById(R.id.precio)
        private val cambio: TextView = view.findViewById(R.id.cambio)
        fun bind(clickListener: SearchClickListener, item: BestMatches) {
            itemView.setOnClickListener {
                clickListener.onclick(item)
            }
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

    class FavouriteViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view.rootView) {
        private val stock: TextView = view.findViewById(R.id.stock)
        private val precio: TextView = view.findViewById(R.id.precio)
        private val cambio: TextView = view.findViewById(R.id.cambio)
        fun bind(clickListener: SearchClickListener, item: BestMatches) {
            itemView.setOnClickListener {
                clickListener.onclick(item)
            }
            stock.text = item.symbol
            precio.text = item.currency
            cambio.text = item.matchScore
        }

        companion object {
            fun from(parent: ViewGroup): FavouriteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_element, parent, false)
                return FavouriteViewHolder(view)
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

class SearchClickListener(val clickListener: (symbol: String) -> Unit) {
    fun onclick(item: BestMatches) = clickListener(item.symbol)
}

sealed class Symbol {
    data class FavoriteSymbol(val bestMatches: BestMatches) : Symbol() {
        override val symbol = bestMatches.symbol
    }

    object Header : Symbol() {
        override val symbol = String()
    }

    abstract val symbol: String
}