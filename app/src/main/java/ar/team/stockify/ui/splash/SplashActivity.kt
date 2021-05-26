package ar.team.stockify.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ar.team.stockify.BuildConfig
import ar.team.stockify.R
import ar.team.stockify.UserActivity
import ar.team.stockify.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.version.text = resources.getString(R.string.version, BuildConfig.VERSION_NAME)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, UserActivity::class.java))
            finish()
        }, 3000)
    }
}