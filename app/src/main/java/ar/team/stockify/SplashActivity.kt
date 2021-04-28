package ar.team.stockify

import android.content.Intent
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import ar.team.stockify.search.SearchActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val version: TextView = findViewById(R.id.version)
        version.text = resources.getString(R.string.version, packageInfo.versionName)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        }, 3000)
    }
}