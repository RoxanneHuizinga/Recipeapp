package com.example.recipeapp.overview

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.instruction.Instruction
import com.example.recipeapp.R
import kotlinx.android.synthetic.main.item_overview_instructions.view.*


/*
 * An List of Instruction objects is added to the class constructor
 * so the RecyclerView knows which Instruction objects it needs to display.
 */
class OverviewInstructionAdapter (private val overviewInstructions: List<Instruction>) : RecyclerView.Adapter<OverviewInstructionAdapter.ViewHolder>() {
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
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_overview_instructions, parent, false))
    }

    // Size of Instructions.
    override fun getItemCount(): Int {
        return overviewInstructions.size
    }

    // Bind method to bind the data to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(overviewInstructions[position])
    }

    /*
     * The ViewHolders bind method uses kotlin synthetics to get the
     * references from the layout file for the ImageView and TextView.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(instruction: Instruction) {

            // Make text partly bold.
            val nextLine = System.getProperty("line.separator")
            var myString = instruction.step + nextLine + instruction.instruction
            val stringBuilder = SpannableStringBuilder (myString)
            val bold = StyleSpan(Typeface.BOLD)
            stringBuilder.setSpan(bold, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            itemView.tvOverviewInstruction.text = stringBuilder
        }
    }
}
