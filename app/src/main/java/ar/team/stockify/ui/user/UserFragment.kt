package ar.team.stockify.ui.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.navArgs

import ar.team.stockify.R
import ar.team.stockify.databinding.UserFragmentBinding
import ar.team.stockify.domain.User
import ar.team.stockify.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: UserFragmentBinding
    private lateinit var currentPhotoName: String
    private val args: UserFragmentArgs by navArgs()


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

    private val requestImage = registerForActivityResult(ActivityResultContracts.TakePicture()) { isGranted ->
        when {
            isGranted -> {
                binding.image.setImageBitmap(loadPhoto(currentPhotoName))
            }
            else -> {
                Toast.makeText(requireContext(), requireContext().getString(R.string.error_save_image_user), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: UserViewModel.UiUserModel) {
        when(model) {
            is UserViewModel.UiUserModel.NoUser -> {
                onNoUser()
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
                if(binding.userNameEdit.text!=null && this::currentPhotoName.isInitialized && File(getAvatarPath(currentPhotoName)).exists()) {
                    userViewModel.saveUser(binding.userNameEdit.text.toString(), currentPhotoName)
                } else {
                    Toast.makeText(requireContext(), requireContext().getText(R.string.error_validate_info_user), Toast.LENGTH_LONG).show()
                }
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
        binding.apply {
            titleUser.text = requireContext().getString(R.string.title_content_user)
            loadPhoto(user.avatar)?.let { photo ->
                image.setImageBitmap(photo)
            }
            userName.text = user.name

            userName.visibility=View.VISIBLE
            button.visibility = View.INVISIBLE
            imageButton.visibility=View.GONE
            userNameLayout.visibility=View.GONE
        }

        if(args.isFromSplash)
            startSearch()
    }


    private fun onNoUser(){
        binding.apply {
            titleUser.text = requireContext().getString(R.string.title_no_user)
            imageButton.text= requireContext().getString(R.string.add_image_user)
            image.setImageResource(R.drawable.ic_menu_user)

            userName.visibility=View.GONE
            userNameLayout.visibility=View.VISIBLE
            imageButton.visibility=View.VISIBLE
            button.visibility = View.VISIBLE
        }

    }

    private fun startSearch() {
        Handler(Looper.getMainLooper()).postDelayed({
            val destination =   ActivityNavigator(requireContext())
               .createDestination()
               .setIntent(Intent(requireContext(), MainActivity::class.java))
            ActivityNavigator(requireContext()).navigate(destination,null,null,null)
            activity?.finish()

        }, 3000)
    }


    private fun loadPhoto(nameFile : String): Bitmap? {
        val file = File(getAvatarPath(nameFile))
        if (file.exists())
            return BitmapFactory.decodeFile(file.absolutePath)
        return null
    }

    private fun getAvatarPath(imageUser : String) =
        requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath.toString() + "/" +imageUser

    private fun checkCamera(): Boolean {
        // Check whether your app is running on a device that has a front-facing camera.
        return requireContext().packageManager.hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT
        )
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                     val photoURI = FileProvider.getUriForFile(
                            requireContext(),
                            "ar.team.stockify.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    currentPhotoName = photoURI.lastPathSegment!!
                    requestImage.launch(photoURI)
                }
            }
        }
    }



    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                "avatar", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        )
    }

}
