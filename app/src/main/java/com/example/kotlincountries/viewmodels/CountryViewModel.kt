package com.example.kotlincountries.viewmodels

import android.app.Application
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.services.CountryDatabase
import com.example.kotlincountries.view.fragments.AddFragmentDirections
import com.example.kotlincountries.view.fragments.CountryFragmentDirections
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) :BaseViewModel(application) {

    var countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid:Int){
        launch {
            val dao = CountryDatabase.getInstance(getApplication()).getCountryDao()
            countryLiveData.value = dao.getCountry(uuid)
            println("livedatavalue"+ countryLiveData.value)
        }
    }

    fun updateCountry(countryName:String,capital:String,region:String,language:String,currency:String,button: Button){
        launch{
            val db = CountryDatabase.getInstance(getApplication())
            val dao = db.getCountryDao()
            countryLiveData.value?.countryName = countryName
            countryLiveData.value?.countryCapital = capital
            countryLiveData.value?.countryRegion = region
            countryLiveData.value?.countryCurrency = currency
            countryLiveData.value?.countryLanguage = language

            countryLiveData.value?.let { dao.updateCountry(it) }
            val action = CountryFragmentDirections.actionCountryFragmentToFeedFragment()
            Navigation.findNavController(button).navigate(action)
        }
    }
}