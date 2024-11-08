package com.example.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlin.concurrent.Volatile

class CustomSharedPreferences {

    companion object {

        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferences:SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences?= null
        private val lock = Any()

        operator fun invoke(context: Context): CustomSharedPreferences {
            // Synchronize on the lock object
            return synchronized(lock) {
                // Check if instance is null
                if (instance == null) {
                    // Initialize the instance if it is null
                    instance = makeCustomSharedPreferences(context)
                }
                // Return the initialized instance
                instance!!
            }
        }


        private fun makeCustomSharedPreferences(context:Context):CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences((context))
            return CustomSharedPreferences()
        }
    }

    fun saveTime(time:Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(PREFERENCES_TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)
}