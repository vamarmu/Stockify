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

private const val TYPE_HEADER_ITEM = 0
private const val TYPE_FAVOURITE_ITEM = 1

class FavouritesAdapter(private val clickListener: FavouriteClickListener) :
    ListAdapter<SealedStock, RecyclerView.ViewHolder>(FavouriteItemDiffCallback()) {

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
        fun bind(clickListener: FavouriteClickListener, item: Stock) {
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

    private var listAux: MutableList<SealedStock> = mutableListOf()


    fun addListWithHeader(list: List<Stock>?) {

        val symbols = when (list) {
            null -> listOf(SealedStock.Header)
            else -> listOf(SealedStock.Header) + list.map { SealedStock.FavoriteStock(it) }
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
                val item = getItem(position) as SealedStock.FavoriteStock
                holder.bind(clickListener, item.stock)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SealedStock.Header -> TYPE_HEADER_ITEM
            is SealedStock.FavoriteStock -> TYPE_FAVOURITE_ITEM
        }
    }

}

class FavouriteItemDiffCallback : DiffUtil.ItemCallback<SealedStock>() {
    override fun areItemsTheSame(oldItem: SealedStock, newItem: SealedStock): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(oldItem: SealedStock, newItem: SealedStock): Boolean {
        return oldItem == newItem
    }
}


class FavouriteClickListener(val clickListener: (stock: Stock) -> Unit) {
    fun onclick(item: Stock) = clickListener(item)
}

sealed class SealedStock {
    data class FavoriteStock(val stock: Stock) : SealedStock() {
        override val symbol = stock.symbol
    }

    object Header : SealedStock() {
        override val symbol = String()
    }

    abstract val symbol: String
}