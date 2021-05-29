package ar.team.stockify.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.*
import ar.team.stockify.databinding.ActivityDetailsBinding
import ar.team.stockify.model.QuarterlyEarning
import ar.team.stockify.ui.model.BestMatchesDataView


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val DATA = "DetailActivity:detail"
    }


    private val detailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailsData = intent.getParcelableExtra<BestMatchesDataView>(DATA)
       // val detailsDataName = intent.getSerializableExtra(DATA.name).toString()

        if (detailsData != null) {
            binding.ttDetailsCompanySymbol.text = detailsData.symbol
            binding.tDetailsCompanyName.text = detailsData.name
            detailsViewModel.onQueryCompanyDetails(detailsData.symbol)
        }

        detailsViewModel.detailsQuarter.observe(this, { list: List<QuarterlyEarning> ->
            if(list.isNotEmpty()) {
                bindDetailInfo1(binding.result, list)
                bindDetailInfo2(binding.result2, list)
            }
        })

    }

    private fun bindDetailInfo1(result: TextView, list: List<QuarterlyEarning>) {
        result.text = buildSpannedString {

            append("estimated EPS: ")
            bold{ appendLine(list[1].estimatedEPS) }

            append("Fiscal Date Ending: ")
            bold { appendLine(list[1].fiscalDateEnding) }

            append("Reported Date: ")
            bold { appendLine(list[1].reportedDate) }
        }
    }

    private fun bindDetailInfo2(result: TextView, list: List<QuarterlyEarning>) {
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
