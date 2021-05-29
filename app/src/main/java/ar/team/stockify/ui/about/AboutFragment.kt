package ar.team.stockify.ui.about


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.team.stockify.BuildConfig
import ar.team.stockify.R
import ar.team.stockify.databinding.AboutFragmentBinding



class AboutFragment : Fragment() {


    private lateinit var binding : AboutFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.versionName.text = resources.getString(R.string.version, BuildConfig.VERSION_NAME)
        binding.packageName.text = resources.getString(R.string.application_id, BuildConfig.APPLICATION_ID)

    }





}