package com.example.recipeapp.instruction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import kotlinx.android.synthetic.main.item_instruction.view.*

/*
 * An List of Instruction objects is added to the class constructor
 * so the RecyclerView knows which Instruction objects it needs to display.
 */
class InstructionsAdapter (private val instructions: List<Instruction>) : RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {
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
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_instruction, parent, false))
    }

    // Size of Instructions.
    override fun getItemCount(): Int {
        return instructions.size
    }

    // Bind method to bind the data to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(instructions[position])
    }

    /*
     * The ViewHolders bind method uses kotlin synthetics to get the
     * references from the layout file for the ImageView and TextView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(instruction: Instruction) {
            itemView.tvStep.text = instruction.step
            itemView.tvInstruction.text = instruction.instruction
        }
    }
}
