package com.example.thebotanyapp.presentation.screen.detail

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.thebotanyapp.databinding.FragmentDetailBinding
import com.example.thebotanyapp.presentation.base.BaseFragment
import com.example.thebotanyapp.presentation.event.detail.DetailEvent
import com.example.thebotanyapp.presentation.extension.showSnackBar
import com.example.thebotanyapp.presentation.model.plant.PlantModel
import com.example.thebotanyapp.presentation.state.detail.DetailState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailViewModel by viewModels()


    override fun setup() {
        extractUserId()
    }

    override fun viewActionListeners() {
        favouriteButtonSetup()
    }

    override fun observers() {
        observeUserDetail()
    }

    private fun observeUserDetail() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.detailState.collect {
                    handleUserDetail(state = it)
                }
            }
        }
    }

    private fun handleUserDetail(state: DetailState) {
        state.details?.let{
            binding.apply {
                tvName.text = it.name
                tvFamily.text = it.family
                tvSpecies.text = it.species
                tvWateringSchedule.text = it.wateringSchedule
                tvSunlightRequirement.text = it.sunlightRequirement
                tvGrowthHeight.text = it.growthHeight
                tvBloomingSeason.text = it.bloomingSeason
                tvDescription.text = it.description
                Glide.with(ivImage.context)
                    .load(it.image)
                    .into(ivImage)
            }
        }
        state.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.progressBar.isVisible = state.isLoading
    }

    private fun favouriteButtonSetup() {
        binding.addToFavouritesButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.detailState.collect { state ->
                        state.details?.let { plant ->
                            if(!state.isFavourite) {
                                viewModel.onEvent(DetailEvent.AddFavouritePlant(plant = plant))
                                binding.root.showSnackBar("Plant added to favourites")
                                binding.addToFavouritesButton.text = "In favourites"
                            }else{
                                binding.root.showSnackBar("Plant already in favourites")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun extractUserId() {
        val plantId = arguments?.getInt("plantId") ?: -1
        viewModel.onEvent(DetailEvent.GetPlantDetail(plantId = plantId))
    }
}