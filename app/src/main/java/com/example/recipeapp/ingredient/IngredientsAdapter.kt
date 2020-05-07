package com.example.recipeapp.ingredient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import kotlinx.android.synthetic.main.item_ingredient.view.*

/*
 * An List of Ingredient objects is added to the class constructor
 * so the RecyclerView knows which Ingredient objects it needs to display.
 */
class IngredientsAdapter (private val ingredients: List<Ingredient>) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    /*
     *  For the context variable the lateinit declaration has been used to let Kotlin
     *  know that this variable will be initialized later (in the onCreateViewHolder method).
     */
    lateinit var context: Context

    /*
     * In onCreateViewHolder a ViewHolder object is created which inflates the layout file we created (item_ingredient.xml).
     * We will be needing Context later on so a variable context is set.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false))
    }

    // Size of Ingredients.
    override fun getItemCount(): Int {
        return ingredients.size
    }

    // Bind method to bind the data to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    /*
     * The ViewHolders bind method uses kotlin synthetics to get the
     * references from the layout file for the ImageView and TextView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(ingredient: Ingredient) {
            itemView.tvCharacter.text = ingredient.numerationCharacter
            itemView.tvIngredient.text = ingredient.ingredient
        }
    }
}
