package com.example.recipeapp.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.ingredient.Ingredient
import com.example.recipeapp.R
import kotlinx.android.synthetic.main.item_overview_ingredients.view.*


/*
 * An List of Instruction objects is added to the class constructor
 * so the RecyclerView knows which Instruction objects it needs to display.
 */
class OverviewIngredientAdapter (private val overviewIngredients: List<Ingredient>) : RecyclerView.Adapter<OverviewIngredientAdapter.ViewHolder>() {
    /*
     *  For the context variable the lateinit declaration has been used to let Kotlin
     *  know that this variable will be initialized later (in the onCreateViewHolder method).
     */
    lateinit var context: Context

    /*
     * In onCreateViewHolder a ViewHolder object is created which inflates the layout file we created (item_instruction.xml).
     * We will be needing Context later on so a variable context is set.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_overview_ingredients, parent, false))
    }

    // Size of Instructions.
    override fun getItemCount(): Int {
        return overviewIngredients.size
    }

    // Bind method to bind the data to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(overviewIngredients[position])
    }

    /*
     * The ViewHolders bind method uses kotlin synthetics to get the
     * references from the layout file for the ImageView and TextView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ingredient: Ingredient) {
            itemView.tvOverviewIngredients.text = " " + ingredient.numerationCharacter + ingredient.ingredient;
        }
    }
}
