package com.example.thebotanyapp.presentation.screen.forgotpassword

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thebotanyapp.databinding.FragmentForgotPasswordBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.forgotpassword.ForgotPasswordEvent
import com.example.thebotanyapp.presentation.state.forgotpassword.ForgotPasswordState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun viewActionListeners() {
        passwordResetButtonClicked()
    }

    override fun observers() {
        observeNavigationEvents()
        observeForgotPasswordState()
    }

    private fun observeForgotPasswordState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.forgotPasswordState.collect {
                    handleForgotPasswordState(forgotPasswordState = it)
                }
            }
        }
    }

    private fun observeNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleForgotPasswordState(forgotPasswordState: ForgotPasswordState) {
        binding.progressBar.isVisible = forgotPasswordState.isLoading
        if (forgotPasswordState.isLoading) View.VISIBLE else View.GONE

        forgotPasswordState.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(ForgotPasswordEvent.ResetErrorMessage)
        }
    }

    private fun passwordResetButtonClicked() {
        binding.btnReset.setOnClickListener {
            handlePasswordReset()
        }
    }

    private fun handlePasswordReset() {
        val email = binding.etEmail.text.toString()
        viewModel.onEvent(ForgotPasswordEvent.SendPasswordLink(email = email))
    }

    private fun handleNavigationEvents(event: ForgotPasswordViewModel.ForgotPasswordNavigationEvent) {
        when (event) {
            is ForgotPasswordViewModel.ForgotPasswordNavigationEvent.NavigateToLogin -> findNavController().navigate(
                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLogInFragment()
            )
        }
    }
}