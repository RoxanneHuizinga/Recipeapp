package com.example.recipeapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.instruction.Instruction
import com.example.recipeapp.R
import com.example.recipeapp.instruction.InstructionsAdapter
import kotlinx.android.synthetic.main.activity_ingredients.*
import kotlinx.android.synthetic.main.activity_instructions.*
import kotlinx.android.synthetic.main.activity_instructions.btnDone


class InstructionsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var view: View

    private val instructionList = arrayListOf<Instruction>()
    private val instructionAdapter = InstructionsAdapter(instructionList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        btnAddInstruction.setOnClickListener {
            onAddClick()
        }

        btnDone.setOnClickListener {
            onDoneClick()
        }

        setToolbar()
        initViews()
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

    private fun setToolbar() {
        view = findViewById(R.id.parentInstructions)
        toolbar = view.findViewById(R.id.toolbarInstructions)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                // Delete ingredient at this position.
                instructionList.removeAt(position)
                instructionAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun onAddClick() {



        hideKeyboard(this.rvInstructions)

        // Check whether the texts are not empty.
        if (etAddStep.text.toString().isNotBlank() && etAddInstruction.text.toString().isNotBlank()) {

            val stepText = etAddStep.text.toString()

            val instruction = Instruction("Step ${stepText}.", etAddInstruction.text.toString())

            // Add this ingredient to the list. This list will now be shown in adapter and thus in the RecyclerView.
            instructionList.add(instruction)
            instructionAdapter.notifyDataSetChanged()

            // Input field is cleared after.
            etAddStep.text?.clear()
            etAddInstruction.text?.clear()
        } else {
            Toast.makeText(this, "One or more of the input fields are empty.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onDoneClick() {

        // Check whether RecyclerView has items.
        if (instructionAdapter.itemCount != 0) {

            val resultIntent = Intent()

            // Add data from the previous activity along as data.
            resultIntent.putExtras(intent)

            resultIntent.putExtra("RECIPE_INSTRUCTIONS_LIST", instructionList)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, "Please fill in at least one instruction.", Toast.LENGTH_LONG).show()
        }

        //Fading animation when going from InstructionsActivity to HomeActivity.
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun initViews() {
        rvInstructions.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvInstructions.adapter = instructionAdapter

        createItemTouchHelper().attachToRecyclerView(rvIngredients)
    }

    // Hide keyboard function.
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
