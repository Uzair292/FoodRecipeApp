package com.example.foodrecipeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipe_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipeEntity>>
}
