package com.example.foodrecipeapp.data

import com.example.foodrecipeapp.data.interfaces.FoodRecipeApi
import com.example.foodrecipeapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipeApi   //here dagger/hilt will provide this field here, if we initialized it inside NetworkModule.kt
) {
    suspend fun getRecipes(queries: Map<String,String>) : Response<FoodRecipe>{
        return  foodRecipeApi.getRecipes(queries)
    }
}