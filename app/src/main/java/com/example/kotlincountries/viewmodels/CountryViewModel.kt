package com.example.kotlincountries.viewmodels

import android.app.Application
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.services.CountryDatabase
import com.example.kotlincountries.services.FirestoreCountryOperations
import com.example.kotlincountries.view.fragments.AddFragmentDirections
import com.example.kotlincountries.view.fragments.CountryFragmentDirections
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) :BaseViewModel(application) {

    var countryLiveData = MutableLiveData<Country>()
    val db = FirestoreCountryOperations()

    fun getDataFromRoom(uuid:Int){
        launch {
            val dao = CountryDatabase.getInstance(getApplication()).getCountryDao()
            countryLiveData.value = dao.getCountry(uuid)
            println("livedatavalue"+ countryLiveData.value)
        }
    }

    fun getDataFromFireStore(countryName:String){
        launch {
            db.getCountryByName(
                countryName,
                onSuccess = {countryLiveData.value = it},
                onFailure = { Toast.makeText(getApplication(),"Unable to get country information",Toast.LENGTH_LONG).show()}
            )
            println("livedatavalue"+ countryLiveData.value)
        }
    }

    fun updateCountry(name:String,capital:String,region:String,language:String,currency:String,button: Button){
        launch{
            // val db = CountryDatabase.getInstance(getApplication())
            // val dao = db.getCountryDao()

            countryLiveData.value?.countryName?.let {
                db.updateCountry(
                    it,name,
                    mapOf(
                        "capital" to capital,
                        "region" to region,
                        "language" to language,
                        "currency" to currency,
                    ),
                    onSuccess = {},
                    onFailure = {}
                )
            }

            val action = CountryFragmentDirections.actionCountryFragmentToFeedFragment()
            Navigation.findNavController(button).navigate(action)
        }
    }
}