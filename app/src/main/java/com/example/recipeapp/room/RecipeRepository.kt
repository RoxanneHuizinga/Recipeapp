package com.example.recipeapp.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipeapp.recipe.Recipe

class RecipeRepository (context: Context) {

    private val recipeDao : RecipeDao

    init {
        val recipeRoomDatabase = RecipeRoomDatabase.getDatabase(context)
        recipeDao = recipeRoomDatabase!!.recipeDao()
    }

    fun getAllRecipes() : LiveData<List<Recipe>> = recipeDao.getAllRecipes()
    suspend fun insertRecipe(recipe: Recipe)  = recipeDao.insertRecipe(recipe)
    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.deleteRecipe(recipe)
    suspend fun deleteAllRecipes() = recipeDao.deleteAllRecipes()
}