package com.example.thebotanyapp.presentation.screen.favourites

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thebotanyapp.databinding.FragmentFavouritesBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.favourites.FavouritesEvent
import com.example.thebotanyapp.presentation.extension.showSnackBar
import com.example.thebotanyapp.presentation.model.plant.PlantModel
import com.example.thebotanyapp.presentation.state.favourites.FavouritesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(FragmentFavouritesBinding::inflate) {

    private val viewModel: FavouritesViewModel by viewModels()
    private lateinit var listAdapter: FavouritesRecyclerAdapter

    override fun setup() {
        initRecyclerView()
    }

    override fun viewActionListeners() {
        searchListener()
    }

    override fun observers() {
        observeUserFavourites()
        observeNavigationEvents()
    }

    private fun initRecyclerView() {
        listAdapter = FavouritesRecyclerAdapter(
            onButtonClick = {
                handleButtonClick(it)
            },
            onItemClick = {
                handleItemClick(it)
            }
        )
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
        viewModel.onEvent(FavouritesEvent.GetFavouritesList)
    }

    private fun observeUserFavourites() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouritePlant.collect {
                    handleFavouriteList(state = it)
                    Log.d("FavouritesFragment", "State updated: $it")
                }
            }
        }
    }

    private fun observeNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouritesNavigationEvents.collect { event ->
                    handleFavouriteNavigationEvents(event)
                }
            }
        }
    }

    private fun handleSearch(query: String) {
        viewModel.onEvent(FavouritesEvent.FavouritePlantSearch(query = query))
    }

    private fun searchListener() {
        binding.etSearch.addTextChangedListener {
            handleSearch(it.toString())
        }
    }

    private fun handleButtonClick(plant: PlantModel) {
        viewModel.onEvent(FavouritesEvent.RemovePlantFromFavourite(plant = plant))
    }
    private fun handleFavouriteList(state: FavouritesState) {
        state.favourites?.let {
            Log.d("FavouritesFragment", "Submitting list to adapter: ${it.size}")
            listAdapter.submitList(it)
        }
        state.errorMessage?.let {
            binding.root.showSnackBar(it)
        }
    }

    private fun handleItemClick(plant: PlantModel) {
        viewModel.onEvent(FavouritesEvent.PlantItemClicked(plant = plant))
    }

    private fun handleFavouriteNavigationEvents(event: FavouritesViewModel.FavouritesNavigationEvents){
        when(event){
            is FavouritesViewModel.FavouritesNavigationEvents.NavigateToPlantDetails -> findNavController().navigate(
                FavouritesFragmentDirections.actionFavouritesFragmentToDetailFragment(
                    event.plantId
                )
            )
        }
    }
}