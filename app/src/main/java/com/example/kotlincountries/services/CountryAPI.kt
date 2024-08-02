package com.example.kotlincountries.services

import com.example.kotlincountries.model.entities.Country
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryAPI {
    //https://raw.githubusercontent.com/
    // atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>
    // Endpoint to delete a country by ID

    @DELETE("countries/{id}")
    fun deleteCountry(@Path("id") countryId: Int): Single<Unit>


}