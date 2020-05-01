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
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val recipeRepository = RecipeRepository(application.applicationContext)

    //Add a recipe object of type LiveData which has the list of recipes from the repository.
    val recipes: LiveData<List<Recipe>> = recipeRepository.getAllRecipes()

    fun insertRecipe(recipe: Recipe) {
        ioScope.launch {
            recipeRepository.insertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        ioScope.launch {
            recipeRepository.deleteRecipe(recipe)
        }
    }

    fun deleteAllRecipes() {
        ioScope.launch {
            recipeRepository.deleteAllRecipes();
        }
    }
}
//A ViewModel has been made for the MainActivity. The logic that doesn’t concern the user
// interface has been moved to this class. This way we have separated the concerns of ui logic
// and business logic from each other.
//
//We also don’t need the Dispatcher.Main scope anymore because in the ViewModel we don’t have
// any user interface logic.