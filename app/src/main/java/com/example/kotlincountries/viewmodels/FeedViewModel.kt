package com.example.kotlincountries.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.services.CountryAPIService
import com.example.kotlincountries.services.CountryDatabase
import com.example.kotlincountries.util.CustomSharedPreferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private val api = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData() {
        val updateTime = customSharedPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()

        }
        else {
            getDataFromAPI()
        }
    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    fun getDataFromAPI(){
        countryLoading.value = true
        disposable.add(api.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(t: List<Country>) {
                    println("onSuccess Print Line")
                    storeInSQLite(t)
                    Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
                }

                override fun onError(e: Throwable) {
                    countryLoading.value = false
                    countryError.value = true
                    e.printStackTrace()
                }
            })
        )
    }

    private fun showCountries(countryList:List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(countries:List<Country>) {
        launch {
            println("storeInSQLite Print Line")
            val db = CountryDatabase.getInstance(getApplication())
            val dao = db.getCountryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*countries.toTypedArray())
            var i = 0
            while (i < countries.size){
                countries[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(countries)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }

    private fun getDataFromSQLite() {
        countryLoading.value = true
        launch {
            println("getDataFromSQLite Print Line")
            val db = CountryDatabase.getInstance(getApplication())
            val dao = db.getCountryDao()
            val countries = dao.getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}