package com.example.recipeapp.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.ingredient.Ingredient
import com.example.recipeapp.instruction.Instruction
import com.example.recipeapp.overview.OverviewIngredientAdapter
import com.example.recipeapp.overview.OverviewInstructionAdapter
import com.example.recipeapp.viewModel_LiveData.GeneralViewModel
import kotlinx.android.synthetic.main.activity_overview.*
import kotlinx.android.synthetic.main.add_toolbar.view.*

class OverviewActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var view: View

    private val overviewInstructionsList = arrayListOf<Instruction>()
    private val overviewIngredientsList = arrayListOf<Ingredient>()
    private val overviewInstructionAdapter = OverviewInstructionAdapter(overviewInstructionsList)
    private val overviewIngredientAdapter = OverviewIngredientAdapter(overviewIngredientsList)

    private lateinit var viewModel: GeneralViewModel

    private var recipeId: Long? = null
    private var recipeName: String? = null
    private var recipeImage: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        recipeId = intent.getLongExtra("ID", 0)
        recipeName = intent.getStringExtra("NAME")
        recipeImage = intent.getIntExtra("IMAGE", 0)

        viewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java)

        getRecipesFromDatabase()

        initViews()
        setToolbar()
    }

    private fun getRecipesFromDatabase() {
        // Observe recipes from the view model, update the list when the data is changed.
        viewModel.recipes.observe(this, Observer { recipes ->
            this@OverviewActivity.overviewInstructionsList.clear()
            this@OverviewActivity.overviewIngredientsList.clear()

            // Every recipe item.
            for (iterator in recipes.indices) {
                if (recipes[iterator].id == recipeId) {
                    // For everything in ingredientsList in recipe.
                    for (index in recipes[iterator].ingredients!!) {
                        this@OverviewActivity.overviewIngredientsList.add(index)
                    }

                    for (index in recipes[iterator].instructions!!) {
                        this@OverviewActivity.overviewInstructionsList.add(index)
                    }

                    recipeName = recipes[iterator].name
                }
            }

            this@OverviewActivity.overviewIngredientAdapter.notifyDataSetChanged()
            this@OverviewActivity.overviewInstructionAdapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        rvOverviewIngredients.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvOverviewIngredients.adapter = overviewIngredientAdapter

        rvOverviewInstructions.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvOverviewInstructions.adapter = overviewInstructionAdapter
    }

    private fun setToolbar() {
        view = findViewById(R.id.parentOverview)
        toolbar = view.findViewById(R.id.toolbarOverview)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.tvTitle.text = recipeName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                // Fading animation when returning to previous activity.
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
