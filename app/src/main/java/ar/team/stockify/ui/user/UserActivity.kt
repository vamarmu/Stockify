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
import android.util.AttributeSet

import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ar.team.stockify.databinding.ActivityUserBinding
import ar.team.stockify.domain.User
import ar.team.stockify.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityUserBinding

    lateinit var currentPhotoName: String
    private val REQUEST_TAKE_PHOTO = 1


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
        userViewModel.model.observe(this, Observer(::updateUi))

    }



    private fun updateUi(model: UserViewModel.UiUserModel) {
        when(model) {
            is UserViewModel.UiUserModel.NoUser -> {
                onClickImageButton()
                onClickButton()
            }
            is UserViewModel.UiUserModel.Content -> {  bindUser(model.user)}
            is UserViewModel.UiUserModel.Camera -> {
                if (checkCamera()) {
                    requestPermissionLauncher1.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
            is UserViewModel.UiUserModel.Submit -> {
                //TODO Validar que los campos no esten vacios
                userViewModel.saveUser(binding.username.text.toString(),currentPhotoName)

            // TODO llamar Snackbar o Toast con un error
            }
        }
    }





    private fun onClickButton() { binding.button.setOnClickListener {
            userViewModel.onButtonClicked()
        }
    }

    private fun onClickImageButton() {
        binding.imageButton.setOnClickListener {
            userViewModel.onImageButtonClicked()
        }
    }



    private fun bindUser(user : User) {
        binding.button.visibility = View.INVISIBLE
        val file = File(getAvatarPath(user.avatar))
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


    private fun getAvatarPath(imageUser : String) =
        getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString() + "/ " +imageUser



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
