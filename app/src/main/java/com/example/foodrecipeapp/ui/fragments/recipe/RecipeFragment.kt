package com.example.foodrecipeapp.ui.fragments.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.viewmodels.MainViewModel
import com.example.foodrecipeapp.adapters.FoodRecipeAdapter
import com.example.foodrecipeapp.databinding.FragmentRecipeBinding
import com.example.foodrecipeapp.utils.NetworkResult
import com.example.foodrecipeapp.utils.observeOnce
import com.example.foodrecipeapp.viewmodels.RecipeViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private var _binding : FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recipeViewModel: RecipeViewModel
    private val mAdapter: FoodRecipeAdapter by lazy { FoodRecipeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
         recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val shimmer = binding.shimmerFrameLayout
        val rvRecipes = binding.rvRecipes
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel
        setUpRecyclerview(rvRecipes)
        readDatabase(shimmer, rvRecipes)

        binding.btnOpenRecipeBottomSheet.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_recipeBottomSheet)
        }
        return binding.root
    }

    private fun readDatabase(shimmer: ShimmerFrameLayout, recyclerView: RecyclerView){
        lifecycleScope.launch {
            viewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "read database called")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect(shimmer, recyclerView)
                }
                else {
                    Log.d("RecipesFragment", "api database called")
                    getDataFromApi(shimmer,recyclerView)
                }
            }
        }
    }
    private fun getDataFromApi(shimmer: ShimmerFrameLayout, recyclerView: RecyclerView) {
        viewModel.getRecipes(recipeViewModel.applyQueries())
        viewModel.recipeResponse.observe(viewLifecycleOwner) { response ->
            Log.d("response", response.data.toString())
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect(shimmer, recyclerView)
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect(shimmer, recyclerView)
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect(shimmer, recyclerView)
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "read database called")
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun setUpRecyclerview(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showShimmerEffect(shimmer: ShimmerFrameLayout, recyclerView: RecyclerView) {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect(shimmer: ShimmerFrameLayout, recyclerView: RecyclerView) {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}