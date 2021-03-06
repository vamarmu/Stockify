package ar.team.stockify.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.databinding.HeaderSearchElementBinding
import ar.team.stockify.databinding.SearchElementBinding
import ar.team.stockify.domain.Stock
import java.util.*
import java.util.stream.Collectors

private const val TYPE_HEADER_ITEM = 0
private const val TYPE_FAVOURITE_ITEM = 1

class SearchAdapter(private val clickListener: SearchClickListener) :
    ListAdapter<SealedSymbol, RecyclerView.ViewHolder>(SearchItemDiffCallback()),
    SearchImpl {

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
        fun bind(clickListener: SearchClickListener, item: Stock) {
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

     fun addListWithoutHeader(list: List<Stock>?) {
        val symbols = list?.map {
            SealedSymbol.FavoriteSymbol(it)
        }
        submitList(symbols)
        listAux = currentList
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
                holder.bind(clickListener, item.stock)
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
            filter.isNullOrEmpty() -> {
                submitList(listAux)
            }
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N -> {
                val collect =
                    listAux.stream().filter {
                        it.symbol.lowercase(Locale.getDefault()).contains(filter)
                    }.collect(Collectors.toList())
                submitList(collect)
            }
            else -> {
                val aux = listAux
                currentList.forEach {
                    if (it.symbol.lowercase(Locale.getDefault()).contains(filter)) {
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


class SearchClickListener(val clickListener: (stock: Stock) -> Unit) {
    fun onclick(item: Stock) = clickListener(item)
}

sealed class SealedSymbol {
    data class FavoriteSymbol(val stock: Stock) : SealedSymbol() {
        override val symbol = stock.symbol
    }

    object Header : SealedSymbol() {
        override val symbol = String()
    }

    abstract val symbol: String
}