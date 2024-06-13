package com.example.thebotanyapp.presentation.screen.session

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thebotanyapp.R
import com.example.thebotanyapp.databinding.FragmentSessionBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SessionFragment : BaseFragment<FragmentSessionBinding>(FragmentSessionBinding::inflate) {

    private val sessionViewModel : SessionViewModel by viewModels()

    override fun viewActionListeners() {
    }

    override fun observers() {
        bindNavigationFlow()
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sessionViewModel.sessionNavigationEvent.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event : SessionViewModel.SessionNavigationEvent) {
        when(event) {
            is SessionViewModel.SessionNavigationEvent.NavigateToHome -> {
                navigateToHomePage()
            }
            is SessionViewModel.SessionNavigationEvent.NavigateToWelcome -> {
                navigateToWelcomePage()
            }
        }
    }

    private fun navigateToHomePage() {
        if (findNavController().currentDestination?.id == R.id.sessionFragment) {
            findNavController().navigate(R.id.action_sessionFragment_to_HomeFragment)
        }
    }

    private fun navigateToWelcomePage() {
        findNavController().navigate(R.id.action_sessionFragment_to_welcomeFragment)
    }
}