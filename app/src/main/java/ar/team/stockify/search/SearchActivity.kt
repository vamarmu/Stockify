package ar.team.stockify.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import ar.team.stockify.R

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val fragment= FavouritesFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val searchView = findViewById<SearchView>(R.id.searchview)
        searchView.setOnQueryTextListener(this)

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, fragment).commit()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //fragment.onQueryTextSubmit()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //fragment.onQueryTextChange()
        return false
    }
}