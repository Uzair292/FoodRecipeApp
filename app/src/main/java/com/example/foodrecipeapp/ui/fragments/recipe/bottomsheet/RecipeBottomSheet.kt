package com.example.foodrecipeapp.ui.fragments.recipe.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.databinding.RecipeBottomSheetBinding
import com.example.foodrecipeapp.utils.Constant.DEFAULT_DIET_TYPE
import com.example.foodrecipeapp.utils.Constant.DEFAULT_MEAL_TYPE
import com.example.foodrecipeapp.viewmodels.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class RecipeBottomSheet : BottomSheetDialogFragment() {

    private var _binding: RecipeBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipeViewModel : RecipeViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel = ViewModelProvider(requireActivity())[RecipeViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  RecipeBottomSheetBinding.inflate(inflater, container, false)

        recipeViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner){value->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }
        binding.mealTypeChipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            val chip = chipGroup.findViewById<Chip>(checkedId)
            val selectedMealType = chip?.text?.toString()?.toLowerCase(Locale.ROOT)
            if (selectedMealType != null) {
                mealTypeChip = selectedMealType
            }
            mealTypeChipId = checkedId
        }
        binding.dietTypeChipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            val chip = chipGroup.findViewById<Chip>(checkedId)
            val selectedDietType = chip?.text?.toString()?.toLowerCase(Locale.ROOT)
            if (selectedDietType != null) {
                dietTypeChip = selectedDietType
            }
            dietTypeChipId = checkedId
        }
        binding.applyBtn.setOnClickListener {
            recipeViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
//            val action = RecipeBottomSheetDirections.actionRecipeBottomSheetToRecipeFragment(true)
//            findNavController().navigate(action)
            findNavController().navigate(R.id.action_recipeBottomSheet_to_recipeFragment)

        }
        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }
            catch (e: Exception){
                Log.d("Recipe-chip", e.stackTraceToString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}