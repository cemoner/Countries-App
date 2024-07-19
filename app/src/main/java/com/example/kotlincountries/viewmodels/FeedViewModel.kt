package com.example.kotlincountries.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries.model.entities.Country
import com.example.kotlincountries.services.CountryAPIService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class FeedViewModel:ViewModel() {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private val api = CountryAPIService()
    private val disposable = CompositeDisposable()

    fun refreshData() {
        getDataFromAPI()
    }

    fun getDataFromAPI(){
        countryLoading.value = true
        disposable.add(api.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableSingleObserver<List<Country>>(){
                override fun onSuccess(t: List<Country>) {
                    countries.value = t
                    countryError.value = false
                    countryLoading.value = false
                }

                override fun onError(e: Throwable) {
                    countryLoading.value = false
                    countryError.value = true
                    e.printStackTrace()
                }
            })
        )
    }
}