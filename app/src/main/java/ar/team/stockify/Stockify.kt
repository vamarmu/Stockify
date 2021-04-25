package ar.team.stockify

import android.app.Application
import timber.log.Timber

class Stockify: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}