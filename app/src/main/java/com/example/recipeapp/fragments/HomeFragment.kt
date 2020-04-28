package com.example.recipeapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.recipe.Recipe
import com.example.recipeapp.recipe.RecipesAdapter
import com.example.recipeapp.viewModel_LiveData.GeneralViewModel
import kotlinx.android.synthetic.main.content_home.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var recipeId: Long? = null;
    private var recipeName: String? = null;

    private val recipesList = arrayListOf<Recipe>();
    val recipesAdapter = RecipesAdapter(recipesList) {
        recipeId = it.id;
        recipeName = it.name;


    }

    private lateinit var myView: View;
    private lateinit var viewModel: GeneralViewModel;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false);

        return myView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        myView.rootView.ivHoney.setImageResource(R.drawable.honey);

        initViews();
        initViewModel();
    }

    private fun initViews() {
        // Reverse Layout is true.
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true);
        rvRecipes.layoutManager = layoutManager;
        layoutManager.stackFromEnd = true;
        rvRecipes.adapter = recipesAdapter;

        createItemTouchHelper().attachToRecyclerView(rvRecipes);
    }

    // Instead of the getRecipes. Now using initViewModel() with LiveData.
    fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java);

        // Observe recipes from the view model, update the list when the data is changed.
        viewModel.recipes.observe(this, Observer { recipes ->
            this@HomeFragment.recipesList.clear();
            this@HomeFragment.recipesList.addAll(recipes);
            recipesAdapter.notifyDataSetChanged();
        })
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val recipeToDelete = recipesList[position];

                // Delete recipe at this position.
                viewModel.deleteRecipe(recipeToDelete);
            }
        }
        return ItemTouchHelper(callback)
    }
}

