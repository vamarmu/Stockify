package ar.team.stockify.ui.user

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import ar.team.stockify.data.repository.StocksRepository
import ar.team.stockify.data.repository.UserRepository
import ar.team.stockify.databinding.ActivityUserBinding
import ar.team.stockify.network.Keys
import ar.team.stockify.network.SymbolsDataSourceImp
import ar.team.stockify.storage.UserDataSourceImp
import ar.team.stockify.ui.main.MainActivity
import ar.team.stockify.usecases.GetUserUseCase
import ar.team.stockify.usecases.HasUserUseCase
import ar.team.stockify.usecases.SetUserUseCase
import java.io.File
import java.io.IOException


class UserActivity : AppCompatActivity(){

    private val hasUserUseCase: HasUserUseCase = HasUserUseCase(
        UserRepository(
            localDataSource = UserDataSourceImp(getPreferences(Context.MODE_PRIVATE), getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
        )
    )

    private val getUserUseCase: GetUserUseCase = GetUserUseCase(
        UserRepository(
            localDataSource = UserDataSourceImp(getPreferences(Context.MODE_PRIVATE), getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
        )
    )

    private val setUserUseCase: SetUserUseCase = SetUserUseCase(
        UserRepository(
            localDataSource = UserDataSourceImp(getPreferences(Context.MODE_PRIVATE), getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
        )
    )

    lateinit var currentPhotoName: String
    private val REQUEST_TAKE_PHOTO = 1

    private lateinit var binding: ActivityUserBinding

    private val requestPermissionLauncher1 = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        when {
            isGranted -> {
                requestPermissionLauncher2.launch(Manifest.permission.CAMERA)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> "Should show rationale"
        }
    }

    private val requestPermissionLauncher2 = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        when {
            isGranted -> {
                dispatchTakePictureIntent()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> "Should show rationale"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(hasUserUseCase.invoke()) {
            bindUser()
        } else {
            onClickImageButton()
            onClickButton()
        }
    }

    private fun onClickButton() {
        binding.button.setOnClickListener {
            setUserUseCase.invoke(binding.username.text.toString(), currentPhotoName)
            //setSharedPreference("username", binding.username.text.toString())
            //setSharedPreference("user_image", currentPhotoName)
            bindUser()
        }
    }

    private fun onClickImageButton() {
        binding.imageButton.setOnClickListener {
            if (checkCamera()) {
                requestPermissionLauncher1.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    /*private fun hasUser() =
        existSharedPreference("user_image") && existSharedPreference("username") && File(
            getAvatarPath()
        ).exists()

    private fun setSharedPreference(name: String, value: String) {
        val sharedPref = getPreferences(MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(name, value)
            commit()
        }
    }*/

    private fun bindUser() {
        val user = getUserUseCase.invoke()
        binding.button.visibility = View.INVISIBLE
        val file = File(user.avatar)
        val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
        binding.imageButton.setImageBitmap(myBitmap)
        binding.username.inputType = 0
        binding.username.setText(user.name)
        startSearchActivity()
    }

    private fun startSearchActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    /*
    private fun getAvatarPath() =
        getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString() + "/" + getSharedPreference("user_image")

    private fun existSharedPreference(name: String) = getPreferences(Context.MODE_PRIVATE).contains(name)

    private fun getSharedPreference(name: String): String? {
        return getPreferences(Context.MODE_PRIVATE).getString(name, null)
    }
    */

    private fun checkCamera(): Boolean {
        // Check whether your app is running on a device that has a front-facing camera.
        return applicationContext.packageManager.hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT
        )
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "ar.team.stockify.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    currentPhotoName = photoURI.lastPathSegment!!
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                "avatar", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        )
    }

}
