package ar.team.stockify.storage

import android.content.SharedPreferences
import ar.team.stockify.data.source.LocalDataSource
import ar.team.stockify.domain.User
import java.io.File


class UserDataSourceImp(sharedPreferences: SharedPreferences, externalDir: File) : LocalDataSource {

    private val sharedPreferences = sharedPreferences
    private val externalDir = externalDir

    override fun getUser(): User {
       return User(getSharedPreference("username")!!, getSharedPreference("user_image")!!)
    }

    override fun hasUser(): Boolean {
        return existSharedPreference("user_image") && existSharedPreference("username") && File(
            getAvatarPath()
        ).exists()
    }

    override fun setUser(name: String, avatar: String): User {
        setSharedPreference("username", name)
        setSharedPreference("user_image", avatar)
        return getUser()
    }

    private fun getSharedPreference(name: String): String? {
        return sharedPreferences.getString(name, null)
    }

    private fun setSharedPreference(name: String, value: String) {
        val sharedPref = sharedPreferences ?: return
        with(sharedPref.edit()) {
            putString(name, value)
            commit()
        }
    }

    private fun getAvatarPath() =
        externalDir.absolutePath.toString() + "/" + getSharedPreference("user_image")

    private fun existSharedPreference(name: String) = sharedPreferences.contains(name)
}