package com.example.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.databinding.RecipesRowLayoutBinding
import com.example.foodrecipeapp.models.FoodRecipe
import com.example.foodrecipeapp.models.Result
import com.example.foodrecipeapp.utils.RecipesDiffUtil

class FoodRecipeAdapter: RecyclerView.Adapter<FoodRecipeAdapter.MyViewHolder>() {
    var recipes = emptyList<Result>()
    class MyViewHolder(private val binding: RecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipes[position]
        holder.bind(currentResult)
    }

    //not recommended
//    fun setData(newData : FoodRecipe){
//        recipes = newData.results
//        notifyDataSetChanged()
//    }

    fun setData(newData : FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResultv = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResultv.dispatchUpdatesTo(this)
    }

}