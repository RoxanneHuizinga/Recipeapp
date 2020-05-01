package com.example.recipeapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipeapp.recipe.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    // LiveData: The generated code runs the query asynchronously on a background thread when needed.
    //This is the reason why we should remove the suspend keyword because we won’t be needing Coroutines
    // for this method anymore since it’s already running on a background thread.
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("DELETE FROM recipe_table")
    suspend fun deleteAllRecipes()

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}