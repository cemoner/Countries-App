package com.example.kotlincountries.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.entities.Country

class FeedViewModel:ViewModel() {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val country = Country("Turkey","Asia","Ankara","TRY","www.ss.com","Turkish")
        val country2 = Country("France","Europe","Paris","EUR","www.ss.com","French")
        val country3 = Country("Germany","Europe","Berlin","EUR","www.ss.com","German")
        val countryList = arrayListOf<Country>(country,country2,country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
}