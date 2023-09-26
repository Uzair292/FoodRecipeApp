package com.example.foodrecipeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipeapp.models.FoodRecipe
import com.example.foodrecipeapp.utils.Constant.RECIPE_TABLE

@Entity(tableName = RECIPE_TABLE)
class RecipeEntity(
    var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}