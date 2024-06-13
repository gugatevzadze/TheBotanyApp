package com.example.thebotanyapp.presentation.screen.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thebotanyapp.databinding.FragmentWelcomeBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.welcome.WelcomeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun viewActionListeners() {
        handleRegisterBtnClick()
        handleLoginBtnClick()
    }

    override fun observers() {
        observeNavigationEvent()
    }

    private fun handleRegisterBtnClick() {
        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(WelcomeEvent.RegisterButtonClicked)
        }
    }

    private fun handleLoginBtnClick() {
        binding.btnLogIn.setOnClickListener {
            viewModel.onEvent(WelcomeEvent.LoginButtonClicked)
        }

    }

    private fun observeNavigationEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.welcomeNavigationEvent.collect { navigationEvent ->
                    handleNavigationEvent(navigationEvent)
                }
            }
        }
    }

    private fun handleNavigationEvent(event: WelcomeViewModel.WelcomeNavigationEvent) {
        when (event) {
            is WelcomeViewModel.WelcomeNavigationEvent.NavigateToLogin -> findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment()
            )
            is WelcomeViewModel.WelcomeNavigationEvent.NavigateToRegister -> findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
            )
        }
    }
}