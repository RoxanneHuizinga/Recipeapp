package com.example.recipeapp.room

import android.net.Uri
import androidx.room.TypeConverter
import com.example.recipeapp.ingredient.Ingredient
import com.example.recipeapp.instruction.Instruction
import com.google.gson.Gson

class DataConverter {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri.toString()
    }

    @TypeConverter
    fun instructionsListToJson(value: ArrayList<Instruction>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToInstructionsList(value: String): ArrayList<Instruction>? {

        val objects = Gson().fromJson(value, Array<Instruction>::class.java) as Array<Instruction>
        val list = objects.toCollection(ArrayList())
        return list
    }

    @TypeConverter
    fun ingredientsListToJson(value: ArrayList<Ingredient>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToIngredientsList(value: String): ArrayList<Ingredient>? {

        val objects = Gson().fromJson(value, Array<Ingredient>::class.java) as Array<Ingredient>
        val list = objects.toCollection(ArrayList())
        return list
    }
}
