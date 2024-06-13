package com.example.thebotanyapp.presentation.screen.register

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thebotanyapp.databinding.FragmentRegisterBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.register.RegisterEvent
import com.example.thebotanyapp.presentation.state.register.RegisterState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun viewActionListeners() {
        registerButtonClicked()
    }

    override fun observers() {
        observeNavigationEvents()
        observeRegisterState()
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    handleRegisterState(registerState = it)
                }
            }
        }
    }

    private fun observeNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerNavigationEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun registerButtonClicked() {
        binding.btnRegister.setOnClickListener {
            handleRegister()
            sendRegisterResult()
        }
    }

    private fun handleRegister() {
        viewModel.onEvent(
            RegisterEvent.Register(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
                confirmPassword = binding.etConfirmPassword.text.toString()
            )
        )
    }
    private fun sendRegisterResult() {
        setFragmentResult(
            "registerResult",
            bundleOf(
                "email" to binding.etEmail.text.toString(),
                "password" to binding.etPassword.text.toString()
            )
        )
    }

    private fun handleRegisterState(registerState: RegisterState) {
        binding.progressBar.isVisible = registerState.isLoading
        if (registerState.isLoading) View.VISIBLE else View.GONE

        registerState.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(RegisterEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: RegisterViewModel.RegisterNavigationEvent) {
        when (event) {
            is RegisterViewModel.RegisterNavigationEvent.NavigateToLogin ->
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
        }
    }
}