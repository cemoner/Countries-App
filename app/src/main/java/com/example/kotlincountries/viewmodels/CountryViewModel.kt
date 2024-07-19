package com.example.kotlincountries.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.entities.Country

class CountryViewModel:ViewModel() {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("Turkey","Ankara","Asia","TRY","www.ss.com","Turkish")
            countryLiveData.value = country

        println()
    }

}