package ar.team.stockify.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.team.stockify.databinding.DetailsListItemBinding
import ar.team.stockify.model.Symbol

class DetailsAdapter (private val details: List<DetailsListItemBinding>): RecyclerView.Adapter<DetailsAdapter.ViewHolder> () {

    class ViewHolder( private val binding: DetailsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: DetailsListItemBinding) {
            // binding.first.text =  detail.name
            //binding.firstValue.text = detail.symbol
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetailsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(details[position])
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
        // en funcion de la posicion que viewType necesitamos
    }

}
