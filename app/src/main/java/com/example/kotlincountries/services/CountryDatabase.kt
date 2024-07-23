package com.example.kotlincountries.services

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountries.model.entities.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun getCountryDao(): CountryDao

    companion object {

        @Volatile
        private var dbInstance: CountryDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): CountryDatabase {
            return dbInstance ?: synchronized(lock) {
                dbInstance ?: buildDatabase(context).also {
                    dbInstance = it
                }
            }
        }

         fun buildDatabase(context: Context): CountryDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CountryDatabase::class.java,
                "countrydatabase"
            ).build()
        }
    }
}
