package ar.team.stockify.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import ar.team.stockify.R
import ar.team.stockify.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val fragment = SearchFragment.newInstance()
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySearchBinding.inflate(layoutInflater).apply {
            setContentView(root)
            searchView = searchview
        }
        setContentView(R.layout.activity_search)
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
