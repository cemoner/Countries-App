package com.example.kotlincountries.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.services.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) :BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid:Int){
        launch {
            val dao = CountryDatabase.getInstance(getApplication()).getCountryDao()
            countryLiveData.value = dao.getCountry(uuid)
            println("livedatavalue"+ countryLiveData.value)
        }
    }

}