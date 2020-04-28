package com.example.recipeapp.recipe

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.ingredient.Ingredient
import com.example.recipeapp.instruction.Instruction
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipe_table")
data class Recipe (

    // Card information.
    var name: String?,
    var image: Uri?,
    var servings: Int?,
    var preparationTime: Int?,

    // Details screen information.
    var ingredients: ArrayList<Ingredient>?,
    var instructions: ArrayList<Instruction>?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable