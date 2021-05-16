package ar.team.stockify

import android.Manifest
import android.R
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
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import ar.team.stockify.databinding.ActivityUserBinding
import ar.team.stockify.search.SearchActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class UserActivity : AppCompatActivity(){

    lateinit var currentPhotoPath: String
    private val REQUEST_TAKE_PHOTO = 1


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        val message = if(isGranted) "PERMISSION GRANTED" else "PERMISSION REJECTED"
        Toast.makeText(this, message, Toast.LENGTH_LONG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if(sharedPref.contains("username")) {
            //val file = File(sharedPref.getString("user_image", "No image"))
            val path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString() + "/" + sharedPref.getString(
                    "user_image_name",
                    "No image"
            )
            val file: File = File(path)
            Log.i("pathphotoget", path)
            if (file.exists()) {
                val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
                binding.userImage.setImageBitmap(myBitmap)
            }
            binding.username.inputType = 0
            binding.username.setText(sharedPref.getString("username", "No name"))
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, SearchActivity::class.java))
                finish()
            }, 3000)
        } else {
            if (checkCamera()) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                dispatchTakePictureIntent()
            }
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString("username", "Christian González Cañete")
                commit()
            }
        }
    }

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
                    val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
                    with(sharedPref.edit()) {
                        putString("user_image", photoURI.path)
                        putString("user_image_name", photoURI.lastPathSegment)
                        commit()
                    }
                    Log.i("pathphotosave", photoURI.path.toString())
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

}
