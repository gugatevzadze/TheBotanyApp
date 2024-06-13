package com.example.thebotanyapp.presentation.screen.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.thebotanyapp.databinding.FragmentProfileBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.profile.ProfileEvent
import com.example.thebotanyapp.presentation.state.profile.ProfileState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val  viewModel: ProfileViewModel by viewModels()

    private var selectedImageUri: Uri? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedImageUri = data?.data
            Log.d("UserProfileFragment", "Selected Image URI: $selectedImageUri")

            selectedImageUri?.let { uri ->
                viewModel.onEvent(ProfileEvent.UpdateProfilePic(uri = uri))
                Glide.with(requireContext())
                    .load(uri)
                    .into(binding.profilePic)
            }
        }
    }

    override fun setup() {
        retrieveProfilePic()
        setUser()
    }

    override fun viewActionListeners() {
        profilePicBtn()
    }

    override fun observers() {
        profileObserver()
    }

    private fun retrieveProfilePic() {
        viewModel.onEvent(ProfileEvent.RetrieveProfilePic)
    }

    private fun profilePicBtn() {
        with(binding) {
            btnAdd.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryLauncher.launch(galleryIntent)
            }
        }
    }

    private fun profileObserver(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileState.collect { state ->
                    handleProfileState(state = state)
                }
            }
        }
    }

    private fun handleProfileState(state: ProfileState) {
        with(binding) {
            state.profileImage?.let { uri ->
                Glide.with(requireContext())
                    .load(uri)
                    .into(profilePic)
            }
        }
    }

    private fun setUser(){
        val user = FirebaseAuth.getInstance().currentUser?.email
        Log.d("ProfileFragment", "User: $user")
        binding.tvEmail.text = user
    }
}