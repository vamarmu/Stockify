package ar.team.stockify.search

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
    ListAdapter<SealedSymbol, RecyclerView.ViewHolder>(SearchItemDiffCallback()),
    SearchImpl, AddSymbols {

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
        fun bind(clickListener: SearchClickListener, item: BestMatches) {
            itemView.setOnClickListener {
                clickListener.onclick(item)
            }
            stock.text = item.symbol
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_element, parent, false)
                return ViewHolder(view)
            }
        }
    }

    private lateinit var listAux: MutableList<SealedSymbol>
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun addListWithoutHeader(list: List<BestMatches>?) {
        adapterScope.launch {
            val symbols = list?.map {
                SealedSymbol.FavoriteSymbol(it)
            }
            withContext(Dispatchers.Main) {
                submitList(symbols)
            }
        }
    }

    override fun addListWithHeader(list: List<BestMatches>?) {
        adapterScope.launch {
            val symbols = when (list) {
                null -> listOf(SealedSymbol.Header)
                else -> listOf(SealedSymbol.Header) + list.map { SealedSymbol.FavoriteSymbol(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(symbols)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        listAux = currentList
        return when (viewType) {
            TYPE_HEADER_ITEM -> HeaderViewHolder.from(parent)
            TYPE_FAVOURITE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as SealedSymbol.FavoriteSymbol
                holder.bind(clickListener, item.bestMatches)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SealedSymbol.Header -> TYPE_HEADER_ITEM
            is SealedSymbol.FavoriteSymbol -> TYPE_FAVOURITE_ITEM
        }
    }


    override fun onQueryTextSubmit(filter: String) {
        textFilter(filter)
    }

    override fun onQueryTextChange(filter: String) {
        textFilter(filter)

    }

    private fun textFilter(filter: String) {
        when {
            filter.isEmpty() -> {
                submitList(listAux)
            }
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N -> {
                val collect =
                    currentList.stream().filter {
                        it.symbol.contains(filter)
                    }.collect(Collectors.toList())
                submitList(collect)
            }
            else -> {
                val aux = mutableListOf<SealedSymbol>()
                currentList.forEach {
                    if (it.symbol.contains(filter)) {
                        aux.add(it)
                    }
                }
                submitList(aux)
            }
        }
    }
}


class SearchItemDiffCallback : DiffUtil.ItemCallback<SealedSymbol>() {
    override fun areItemsTheSame(oldItem: SealedSymbol, newItem: SealedSymbol): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(oldItem: SealedSymbol, newItem: SealedSymbol): Boolean {
        return oldItem == newItem
    }
}

class SearchClickListener(val clickListener: (symbol: String) -> Unit) {
    fun onclick(item: BestMatches) = clickListener(item.symbol)
}

sealed class SealedSymbol {
    data class FavoriteSymbol(val bestMatches: BestMatches) : SealedSymbol() {
        override val symbol = bestMatches.symbol
    }

    object Header : SealedSymbol() {
        override val symbol = String()
    }

    abstract val symbol: String
}