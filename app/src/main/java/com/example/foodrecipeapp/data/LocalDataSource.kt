package com.example.foodrecipeapp.data

import com.example.foodrecipeapp.data.database.RecipeDao
import com.example.foodrecipeapp.data.database.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val recipeDao : RecipeDao
) {

     fun readDatabase() : Flow<List<RecipeEntity>>{
        return recipeDao.readRecipes()
    }

    fun insertRecipe(
        recipeEntity: RecipeEntity
    ){
        recipeDao.insertRecipe(recipeEntity)
    }
}