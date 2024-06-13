package com.example.thebotanyapp.presentation.screen.list

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thebotanyapp.databinding.FragmentListBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.list.ListEvent
import com.example.thebotanyapp.presentation.model.plant.PlantModel
import com.example.thebotanyapp.presentation.state.list.ListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var listAdapter: ListRecyclerAdapter

    override fun setup() {
        initRecyclerView()
    }

    override fun viewActionListeners() {
        searchListener()
    }

    override fun observers() {
        observeNavigationEvents()
        observeListState()
    }

    private fun initRecyclerView() {
        listAdapter = ListRecyclerAdapter(
            onItemClick = {
                handleItemClick(it)
            }
        )
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = listAdapter
        }
        viewModel.onEvent(ListEvent.GetPlantList)
    }

    private fun observeListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listState.collect {
                    handleListState(state = it)
                }
            }
        }
    }

    private fun observeNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listNavigationEvent.collect {
                    handleNavigationEvent(it)
                }
            }
        }
    }

    private fun handleListState(state: ListState) {
        Log.d("ListFragment", "New list state: $state")
        state.plants?.let {
            Log.d("ListFragment", "New plant list: $it")
            listAdapter.submitList(it)
        }
        state.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.progressBar.isVisible = state.isLoading
    }

    private fun handleItemClick(plant: PlantModel) {
        viewModel.onEvent(ListEvent.PlantItemClick(plant = plant))
    }

    private fun handleSearch(query: String) {
        viewModel.onEvent(ListEvent.PlantSearch(query = query))
    }

    private fun searchListener() {
        binding.etSearch.addTextChangedListener {
            handleSearch(it.toString())
        }
    }

    private fun handleNavigationEvent(event: ListViewModel.ListNavigationEvent) {
        when (event) {
            is ListViewModel.ListNavigationEvent.NavigateToDetail -> findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(event.plantId)
            )
        }
    }
}