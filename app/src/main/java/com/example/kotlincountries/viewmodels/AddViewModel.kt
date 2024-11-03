package com.example.kotlincountries.viewmodels

import android.app.Application
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.services.CountryDatabase
import com.example.kotlincountries.services.FirestoreCountryOperations
import com.example.kotlincountries.view.fragments.AddFragmentDirections
import kotlinx.coroutines.launch

class AddViewModel(application: Application):BaseViewModel(application){
    val db = FirestoreCountryOperations()

    fun addCountry(countryName:String,capital:String,region:String,language:String,currency:String,imageUrl:String,button: Button){
        val country = Country(
            countryName = countryName,
            countryCapital = capital,
            countryRegion = region,
            countryLanguage = language,
            countryCurrency = currency,
            imageUrl = imageUrl
        )
        launch{
//            val db = CountryDatabase.getInstance(getApplication())
//            val dao = db.getCountryDao()
//            dao.insert(country)
            db.addCountry(country,{
                println("Success adding country")
            },{
                println("Error adding country: ${it.message}")
            })
            val action = AddFragmentDirections.actionAddFragmentToFeedFragment()
            Navigation.findNavController(button).navigate(action)
        }
    }

}