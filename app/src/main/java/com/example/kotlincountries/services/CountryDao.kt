package com.example.kotlincountries.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kotlincountries.model.entities.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: Country):List<Long>


    @Query(value = "SELECT * FROM country")
    suspend fun getAllCountries():List<Country>

    @Query(value = "SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId:Int):Country

    @Query(value = "DELETE FROM country")
    suspend fun deleteAllCountries()
}