package ar.team.stockify.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.R
import ar.team.stockify.model.SearchItem

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() , SearchImpl{


    class ViewHolder private constructor(view:View):RecyclerView.ViewHolder(view.rootView){
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

    var list = mutableListOf<SearchItem>()
        set(value) {
            field = value
            list_copia.addAll(list)
            notifyDataSetChanged()
        }

    var list_copia = mutableListOf<SearchItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind(list_copia[position])
    }

    override fun getItemCount(): Int =
       list.size

    override fun onQueryTextSubmit() {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange() {
        TODO("Not yet implemented")
    }

}