package com.example.recipeapp.viewModel_LiveData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipeapp.recipe.Recipe
import com.example.recipeapp.room.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeneralViewModel (application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO);
    private val recipeRepository = RecipeRepository(application.applicationContext);

    val recipes: LiveData<List<Recipe>> = recipeRepository.getAllRecipes();

    fun insertRecipe(recipe: Recipe) {
        ioScope.launch {
            recipeRepository.insertRecipe(recipe);
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        ioScope.launch {
            recipeRepository.deleteRecipe(recipe);
        }
    }

    fun deleteAllRecipes() {
        ioScope.launch {
            recipeRepository.deleteAllRecipes();
        }
    }
}