package ar.team.stockify.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.*
import ar.team.stockify.databinding.ActivityDetailsBinding
import ar.team.stockify.model.QuarterlyEarning


class DetailsActivity : AppCompatActivity() {

    companion object{
        const val DATA = "DetailActivity:detail"
        const val COMPANY_NAME = "name"
    }

    private val detailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailsData = intent.getSerializableExtra(DATA).toString()
        val detailsDataName = intent.getSerializableExtra(COMPANY_NAME).toString()
        Log.d("que es esto???", detailsData)

        binding.ttDetailsCompanySymbol.text = detailsData
        binding.tDetailsCompanyName.text = detailsDataName
        detailsViewModel.onQueryCompanyDetails(detailsData)

        if ( detailsViewModel.detailsQuarter != null) {
            detailsViewModel.detailsQuarter.observe(this, {list: List<QuarterlyEarning> ->
                bindDetailInfo1(binding.result, list)
                bindDetailInfo2(binding.result2, list)
            })
        }
    }

    private fun bindDetailInfo1(result: TextView, list: List<QuarterlyEarning> ) {
        result.text = buildSpannedString {

            bold { append("estimated EPS: ") }
            appendLine(list[1].estimatedEPS)

            bold { append("Fiscal Date Ending: ") }
            appendLine(list[1].fiscalDateEnding)

            bold { append("Reported Date: ") }
            appendLine(list[1].reportedDate)
        }
    }

    private fun bindDetailInfo2(result: TextView, list: List<QuarterlyEarning> ) {
        result.text = buildSpannedString {

            bold { append("Reported EPS: ") }
            appendLine(list[1].reportedEPS)

            bold { appendLine("Surprise: ") }
            appendLine (list[1].surprise)

            bold { append("Surprise Percentage: ") }
            appendLine(list[1].surprisePercentage)}
        }
    }
