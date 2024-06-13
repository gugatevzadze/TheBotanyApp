package com.example.thebotanyapp.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thebotanyapp.databinding.FragmentHomeBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.home.HomeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun viewActionListeners() {
        handleLogoutButtonClicked()
    }

    override fun observers() {
        observeNavigationEvent()
    }

    private fun observeNavigationEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeNavigationEvent.collect { navigationEvent ->
                    handleNavigationEvent(navigationEvent)
                }
            }
        }
    }

    private fun handleLogout() {
        viewModel.onEvent(HomeEvent.Logout)
    }

    private fun handleLogoutButtonClicked() {
        binding.btnLogOut.setOnClickListener {
            handleLogout()
        }
    }

    private fun handleNavigationEvent(event: HomeViewModel.HomeNavigationEvent) {
        when (event) {
            is HomeViewModel.HomeNavigationEvent.NavigateToWelcome -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWelcomeFragment())
        }
    }
}