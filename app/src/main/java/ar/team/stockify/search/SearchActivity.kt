package ar.team.stockify.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import ar.team.stockify.R
import ar.team.stockify.databinding.ActivitySearchBinding
import java.util.*

class SearchActivity : AppCompatActivity(),SearchView.OnQueryTextListener{

    private val fragment = SearchFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)
           // setContentView(R.layout.activity_search)
            setContentView(binding.root)
            val searchView = binding.searchView
            searchView.setOnQueryTextListener(this)

            supportFragmentManager.beginTransaction()
                .add(R.id.frame_layout, fragment).commit()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { fragment.onQueryTextSubmit(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { fragment.onQueryTextChange(it) }
        return false
    }
}
