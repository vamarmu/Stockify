package ar.team.stockify.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import ar.team.stockify.R
import ar.team.stockify.databinding.DetailFragmentBinding
import ar.team.stockify.domain.StockDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        const val DATA = "DetailActivity:detail"
    }

    private val args: DetailFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var binding: DetailFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.detailsToolbar.apply {
            title=""
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@DetailFragment).popBackStack()
            }
        }

        binding.favouritesButton.setOnClickListener {
                detailsViewModel.addRemoveFavourites(args.detailsData)
        }


        binding.ttDetailsCompanySymbol.text = args.detailsData.symbol
        binding.tDetailsCompanyName.text = args.detailsData.name
        detailsViewModel.onQueryCompanyDetails(args.detailsData.symbol)

        detailsViewModel.model.observe(viewLifecycleOwner, Observer{ uiDetailModel ->
            when(uiDetailModel){
                is DetailsViewModel.UiDetailModel.Content -> {
                    if(uiDetailModel.detail.isNotEmpty()) {
                        bindDetailInfo1(binding.result, uiDetailModel.detail)
                        bindDetailInfo2(binding.result2, uiDetailModel.detail)
                    }
                }
                is DetailsViewModel.UiDetailModel.NoContent -> {
                    onNoContent()
                }
                is DetailsViewModel.UiDetailModel.Loading -> {
                    onLoad()
                }
                is DetailsViewModel.UiDetailModel.ToggleFavourite ->{
                    if (uiDetailModel.state) {
                        binding.imgButton.setImageResource(R.drawable.ic_remove_button)
                        binding.textButton.text = getString(R.string.removeButton)
                    }else{
                        binding.imgButton.setImageResource(R.drawable.ic_add_button)
                        binding.textButton.text = getString(R.string.addButton)
                    }
                }
                else -> { onNoContent() }
            }

        })

       detailsViewModel.stockSaved(args.detailsData)
    }

    private fun onNoContent(){
        Toast.makeText(requireContext(),"SIN DATOS", Toast.LENGTH_SHORT).show()
    }

    private fun onLoad(){
        Toast.makeText(requireContext(),"CARGANDO....", Toast.LENGTH_SHORT).show()
    }

    private fun bindDetailInfo1(result: TextView, list: List<StockDetail>) {
        result.text = buildSpannedString {

            append("estimated EPS: ")
            bold{ appendLine(list[1].estimatedEPS) }

            append("Fiscal Date Ending: ")
            bold { appendLine(list[1].fiscalDateEnding) }

            append("Reported Date: ")
            bold { appendLine(list[1].reportedDate) }
        }
    }

    private fun bindDetailInfo2(result: TextView, list: List<StockDetail>) {
        result.text = buildSpannedString {

            append("Reported EPS: ")
            bold { appendLine(list[1].reportedEPS) }

            appendLine("Surprise: ")
            bold { appendLine(list[1].surprise) }

            append("Surprise Percentage: ")
            bold { appendLine(list[1].surprisePercentage) }
        }
    }
}