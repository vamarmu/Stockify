package ar.team.stockify.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.databinding.HeaderSearchElementBinding
import ar.team.stockify.databinding.SearchElementBinding
import ar.team.stockify.model.BestMatches
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.stream.Collectors

private const val TYPE_HEADER_ITEM = 0
private const val TYPE_FAVOURITE_ITEM = 1

class SearchAdapter(private val clickListener: SearchClickListener) :
    ListAdapter<SealedSymbol, RecyclerView.ViewHolder>(SearchItemDiffCallback()),
    SearchImpl, AddSymbols {

    class HeaderViewHolder private constructor(view: HeaderSearchElementBinding) :
        RecyclerView.ViewHolder(view.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val view = HeaderSearchElementBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HeaderViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(view: SearchElementBinding) :
        RecyclerView.ViewHolder(view.root) {
        private val stock: TextView = view.stock
        fun bind(clickListener: SearchClickListener, item: BestMatches) {
            itemView.setOnClickListener {
                clickListener.onclick(item)
            }
            stock.text = item.symbol
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val view =
                    SearchElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(view)
            }
        }
    }

    private var listAux: MutableList<SealedSymbol> = mutableListOf()

    override fun addListWithoutHeader(list: List<BestMatches>?) {
        val symbols = list?.map {
            SealedSymbol.FavoriteSymbol(it)
        }
        submitList(symbols)
        listAux = currentList
    }

    override fun addListWithHeader(list: List<BestMatches>?) {

        val symbols = when (list) {
            null -> listOf(SealedSymbol.Header)
            else -> listOf(SealedSymbol.Header) + list.map { SealedSymbol.FavoriteSymbol(it) }
        }

        submitList(symbols)

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
        listAux = currentList
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
                    listAux.stream().filter {
                        it.symbol.toLowerCase().contains(filter)
                    }.collect(Collectors.toList())
                submitList(collect)
            }
            else -> {
                val aux = listAux
                currentList.forEach {
                    if (it.symbol.toLowerCase(Locale.getDefault()).contains(filter)) {
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