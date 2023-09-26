package com.example.foodrecipeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipeapp.data.repository.DataStoreRepository
import com.example.foodrecipeapp.utils.Constant.API_KEY
import com.example.foodrecipeapp.utils.Constant.DEFAULT_DIET_TYPE
import com.example.foodrecipeapp.utils.Constant.DEFAULT_MEAL_TYPE
import com.example.foodrecipeapp.utils.Constant.DEFAULT_RECIPE_NUMBER
import com.example.foodrecipeapp.utils.Constant.QUERY_NUMBER
import com.example.foodrecipeapp.utils.Constant.QUERY_API_KEY
import com.example.foodrecipeapp.utils.Constant.QUERY_MEAL_TYPE
import com.example.foodrecipeapp.utils.Constant.QUERY_DIET_TYPE
import com.example.foodrecipeapp.utils.Constant.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipeapp.utils.Constant.QUERY_FILL_INGREDIENTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application
) : AndroidViewModel(application)  {
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect{ values ->
                mealType = values.selectedMealType
                dietType = values.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPE_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_MEAL_TYPE] = mealType
        queries[QUERY_DIET_TYPE] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}