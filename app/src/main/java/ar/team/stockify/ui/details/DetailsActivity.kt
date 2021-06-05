package ar.team.stockify.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.*
import ar.team.stockify.R
import ar.team.stockify.databinding.ActivityDetailsBinding
import ar.team.stockify.domain.StockDetail
import ar.team.stockify.model.QuarterlyEarning
import ar.team.stockify.network.model.RemoteQuarterlyEarning
import ar.team.stockify.ui.model.BestMatchesDataView
import ar.team.stockify.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val DATA = "DetailActivity:detail"
    }


    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailsData = intent.getParcelableExtra<BestMatchesDataView>(DATA)
       // val detailsDataName = intent.getSerializableExtra(DATA.name).toString()

        binding.favouritesButton.setOnClickListener {
            if (detailsData != null) {
                detailsViewModel.addRemoveFavourites(detailsData.toStock())
            }
        }

            if (detailsData != null) {
            binding.ttDetailsCompanySymbol.text = detailsData.symbol
            binding.tDetailsCompanyName.text = detailsData.name
            detailsViewModel.onQueryCompanyDetails(detailsData.symbol)
        }


        detailsViewModel.model.observe(this, Observer{ uiDetailModel ->
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

        detailsData?.toStock()?.let { detailsViewModel.stockSaved(it) }



    }

    private fun onNoContent(){
        Toast.makeText(this,"SIN DATOS", Toast.LENGTH_SHORT).show()
    }

    private fun onLoad(){
        Toast.makeText(this,"CARGANDO....", Toast.LENGTH_SHORT).show()
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
