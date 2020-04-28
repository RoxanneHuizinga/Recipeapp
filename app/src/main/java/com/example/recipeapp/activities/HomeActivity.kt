package com.example.recipeapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.recipeapp.ingredient.Ingredient
import com.example.recipeapp.R
import com.example.recipeapp.instruction.Instruction
import com.example.recipeapp.recipe.Recipe
import com.example.recipeapp.viewModel_LiveData.GeneralViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.content_home.*

const val REQUEST_CODE = 100;

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView;
    private lateinit var toolbar: Toolbar;
    private lateinit var view: View;

    private var fragment: Fragment? = null;
    private lateinit var viewModel: GeneralViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragment = supportFragmentManager.findFragmentById(R.id.homeFragment);
        viewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java);

        btnAddRecipe.setOnClickListener {
            onAddClick();
        }

        setToolbar();
        initNavigation();
    }

    private fun onAddClick() {
        // From HomeActivity to AddActivity.
        val nextIntent = Intent(this@HomeActivity, RecipeActivity::class.java);
        startActivityForResult(nextIntent, REQUEST_CODE);

        // Animation to fade into the AddActivity.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private fun setToolbar() {

        bottomNavigation = findViewById(R.id.navView);
        view = findViewById(R.id.parentMain);
        toolbar = view.findViewById(R.id.toolbarHome);

        setSupportActionBar(toolbar);
    }

    private fun initNavigation() {
        // The NavController.
        val navController = findNavController(R.id.navHostFragment);

        // Connect the navHostFragment with the Toolbar.
        val appBarConfiguration = AppBarConfiguration(navController.graph);
        NavigationUI.setupWithNavController(navView, navController);

        // Automatically handles the back button.
        // toolbar.setupWithNavController(navController, appBarConfiguration);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE -> {
                    // On success insert the data in the database.
                    insertInDatabase(data);

                    // Reload this activity to show the results of our database.
                    val refresh = intent;
                    startActivity(refresh);
                    finish();
                }
            }
        }
    }


    private fun insertInDatabase(data: Intent?) {

        // First get all data.
        val recipeName = data?.extras?.getString("RECIPE_NAME");
        val recipeImage = data?.extras?.getString("RECIPE_IMAGE");
        val recipeServings = data?.extras?.getString("RECIPE_SERVINGS");
        val recipePreparationTime = data?.extras?.getString("RECIPE_PREPARATION_TIME");
        val recipeIngredients = data?.extras?.getParcelableArrayList<Ingredient>("RECIPE_INGREDIENTS_LIST");
        val recipeInstructions = data?.extras?.getParcelableArrayList<Instruction>("RECIPE_INSTRUCTIONS_LIST");

        // Then put this in the Recipe.
        val recipe = Recipe(
            name = recipeName,
            image = recipeImage?.toUri(),
            servings = recipeServings?.toInt(),
            preparationTime = recipePreparationTime?.toInt(),
            ingredients = recipeIngredients,
            instructions = recipeInstructions
        )

        // Insert into database.
        viewModel.insertRecipe(recipe);
    }
}


