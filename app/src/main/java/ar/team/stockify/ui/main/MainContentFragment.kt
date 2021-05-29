package ar.team.stockify.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.team.stockify.databinding.MainContentFragmentLayoutBinding


class MainContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MainContentFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }
}