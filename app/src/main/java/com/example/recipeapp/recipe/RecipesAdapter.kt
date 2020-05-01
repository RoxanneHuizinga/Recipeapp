package com.example.recipeapp.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import kotlinx.android.synthetic.main.item_recipe.view.*

/*
 * An List of Recipe objects is added to the class constructor
 * so the RecyclerView knows which Recipe objects it needs to display.
 */
class RecipesAdapter (private val recipes: List<Recipe>, private val onClick: (Recipe) -> Unit) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {
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
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false))
    }

    // Size of Instructions.
    override fun getItemCount(): Int {
        return recipes.size
    }

    // Bind method to bind the data to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    /*
     * The ViewHolders bind method uses kotlin synthetics to get the
     * references from the layout file for the ImageView and TextView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        init {
            itemView.setOnClickListener{
                // AdapterPosition is position of the item represented by the ViewHolder.
                onClick(recipes[adapterPosition])
            }
        }

        fun bind(recipe: Recipe) {
            var favorite = false

            itemView.tvRecipeName.text = recipe.name
            itemView.ivDish.setImageURI(recipe.image)
            itemView.tvServingTime.text = recipe.preparationTime.toString() + " min."
            itemView.tvServingAmount.text = recipe.servings.toString() + " people."

            // Makes it able for the user to favorite and unfavorite.
            itemView.ivFavorite.setOnClickListener {
                if (favorite) {
                    itemView.ivFavorite.setImageResource(R.drawable.ic_favorited)
                    favorite = false
                } else if (!favorite) {
                    itemView.ivFavorite.setImageResource(R.drawable.ic_favorite)
                    favorite = true
                }
            }
        }
    }


}