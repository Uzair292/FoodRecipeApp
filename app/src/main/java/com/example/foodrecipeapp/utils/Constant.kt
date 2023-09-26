package com.example.foodrecipeapp.utils

object Constant {
    val BASE_URL = "https://api.spoonacular.com"
    val API_KEY = "8f3fc13de5f44e529378139332651528"

    //API Query keys
    const val QUERY_SEARCH = "query"
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_MEAL_TYPE = "type"
    const val QUERY_DIET_TYPE = "diet"
    const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"


    const val RECIPE_TABLE = "recipe_table"
    const val DATABASE_NAME = "recipe_database"
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"
    const val DEFAULT_RECIPE_NUMBER = "50"

    //DataStore Repository
    const val PREFERENCES_NAME = "food_recipe_preferences"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCES_DIET_TYPE = "dietType"
    const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    const val PREFERENCES_BACK_ONLINE = "backOnline"
}