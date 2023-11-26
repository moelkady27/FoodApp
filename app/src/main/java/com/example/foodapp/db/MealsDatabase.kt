package com.example.foodapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealsDatabase : RoomDatabase(){
    abstract fun MealDao(): MealDao

    companion object {
        @Volatile
        var INSTANCE: MealsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealsDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealsDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealsDatabase
        }
    }

}