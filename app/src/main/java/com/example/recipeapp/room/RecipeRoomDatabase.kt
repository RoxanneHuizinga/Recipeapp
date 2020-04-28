package com.example.recipeapp.room

import android.content.Context
import androidx.room.*
import com.example.recipeapp.recipe.Recipe

/*
 * Puts together the entities and DAO('s).
 */

// Define what entities to store in our database.

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class RecipeRoomDatabase : RoomDatabase() {

    // Abstract method to get the implementation room makes.
    abstract fun recipeDao(): RecipeDao;

    // We want the database to be static.
    companion object {
        private const val DATABASE_NAME = "RECIPE_DATABASE"

        // Essentially, volatile is used to indicate that a variable's value will be modified by different threads.
        @Volatile
        private var recipeRoomDatabaseInstance: RecipeRoomDatabase? = null

        fun getDatabase(context: Context): RecipeRoomDatabase? {
            if (recipeRoomDatabaseInstance == null) {
                synchronized(RecipeRoomDatabase::class.java) {
                    if (recipeRoomDatabaseInstance == null) {
                        recipeRoomDatabaseInstance = Room.databaseBuilder(context.applicationContext, RecipeRoomDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build();
                    }
                }
            }
            return recipeRoomDatabaseInstance;
        }
    }
}